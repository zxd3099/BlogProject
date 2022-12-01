package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.User;
import com.zxd3099.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxd3099
 * @create 2022-11-20-15:59
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户", description = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(Description = "获取用户信息")
    @ApiOperation(value = "获取用户信息", notes = "需要token")
    public ResponseResult userInfo() {
       return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(Description = "更新用户信息")
    @ApiOperation(value = "更新用户信息", notes = "需要token")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    /**
     * 用户注册接口。
     * 要求：①用户名，昵称，邮箱不能和数据库中原有的数据重复。
     *      ②如果某项重复了注册失败并且要有对应的提示。
     *      ③要求用户名，密码，昵称，邮箱都不能为空。
     *      ④密码必须加密存储
     */
    @PostMapping("/register")
    @SystemLog(Description = "用户注册")
    @ApiOperation(value = "用户注册", notes = "①用户名，昵称，邮箱不能和数据库中原有的数据重复\n" +
            "②如果某项重复了注册失败并且要有对应的提示\n③要求用户名，密码，昵称，邮箱都不能为空\n" +
            "④密码必须加密存储")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "登录用户实体")
    })
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
