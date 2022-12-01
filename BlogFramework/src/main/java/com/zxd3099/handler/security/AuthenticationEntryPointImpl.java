package com.zxd3099.handler.security;

import com.alibaba.fastjson.JSON;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zxd3099
 * @create 2022-11-19-15:08
 * @description 定义认证失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException) throws IOException, ServletException {
        authenticationException.printStackTrace();

        // InsufficientAuthenticationException:登陆凭证不够充分而抛出的异常
        // BadCredentialsException:用户名不存在或者用户密码输入错误
        ResponseResult result = null;
        if (authenticationException instanceof BadCredentialsException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), authenticationException.getMessage());
        } else if (authenticationException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }
        // 响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
