package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.User;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.handler.exception.SystemException;
import com.zxd3099.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxd3099
 * @create 2022-11-18-22:58
 */
@RestController
@Api(tags = "登录", description = "用户登录相关接口")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(Description = "用户登录")
    @ApiOperation(value = "登录", notes = "用户登录接口，需要密码和用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户")
    })
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示，必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        } else if (!StringUtils.hasText(user.getUserPassword())) {
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(Description = "退出登录")
    @ApiOperation(value = "退出登录", notes = "退出登录，需要token")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}
