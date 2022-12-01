package com.zxd3099.service;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.User;

/**
 * @author zxd3099
 * @create 2022-11-18-23:02
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
