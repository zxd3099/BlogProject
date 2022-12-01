package com.zxd3099.service.impl;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.LoginUser;
import com.zxd3099.domain.entity.User;
import com.zxd3099.domain.vo.BlogUserLoginVo;
import com.zxd3099.domain.vo.UserInfoVo;
import com.zxd3099.service.BlogLoginService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.JwtUtil;
import com.zxd3099.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

/**
 * @author zxd3099
 * @create 2022-11-18-23:03
 */
@Service("BlogLoginService")
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判别是否认证通过
        Optional.ofNullable(authenticate).orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        // 获取userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 将用户信息存入redis,key为"bloglogin:" + userId
        redisCache.setCacheObject("bloglogin:"+userId, loginUser);
        // 将token和userinfo封装，返回
        // 把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        // 获取token解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取userid
        Long userId = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }
}
