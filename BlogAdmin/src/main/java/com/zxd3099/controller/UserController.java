package com.zxd3099.controller;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.UserDto;
import com.zxd3099.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxd3099
 * @create 2022-11-30-10:18
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult selectUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String userStatus) {
        return userService.selectUser(pageNum, pageSize, userName, phonenumber, userStatus);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }
}
