package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxd3099.constants.SystemConstants;
import com.zxd3099.domain.entity.LoginUser;
import com.zxd3099.domain.entity.User;
import com.zxd3099.mapper.MenuMapper;
import com.zxd3099.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zxd3099
 * @create 2022-11-18-23:22
 */
@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        // 判断是否查到用户，没有抛出异常
        Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("用户不存在"));
        // 返回用户信息
        if (user.getType().equals(SystemConstants.ADMIN)) {
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, list);
        }
        return new LoginUser(user, null);
    }
}
