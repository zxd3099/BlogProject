package com.zxd3099.utils;

import com.zxd3099.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author zxd3099
 * @create 2022-11-20-15:02
 */
public class SecurityUtils {
    /**
     * 获取用户
     */
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     * @return
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 判断用户是否是管理员
     * @return
     */
    public static Boolean isAdmin() {
        Long id = getUserId();
        return id != null && id.equals(1L);
    }

    /**
     * 获取登录用户的id
     * @return
     */
    public static long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
