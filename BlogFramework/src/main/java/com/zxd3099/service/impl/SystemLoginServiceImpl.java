package com.zxd3099.service.impl;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.LoginUser;
import com.zxd3099.domain.entity.User;
import com.zxd3099.service.LoginService;
import com.zxd3099.utils.JwtUtil;
import com.zxd3099.utils.RedisCache;
import com.zxd3099.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author zxd3099
 * @create 2022-11-26-9:24
 */
@Service
public class SystemLoginServiceImpl implements LoginService {
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
        redisCache.setCacheObject("login:"+userId, loginUser);

        // 将token封装，返回
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        long userId = SecurityUtils.getUserId();
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
