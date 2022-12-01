package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.UserDto;
import com.zxd3099.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-11-18 22:43:01
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult selectUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String userStatus);

    ResponseResult deleteUser(Long id);

    ResponseResult addUser(UserDto userDto);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(UserDto userDto);
}

