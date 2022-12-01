package com.zxd3099.service.impl;

import com.zxd3099.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxd3099
 * @create 2022-11-28-20:47
 */
@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission) {
        // 如果是超级管理员  直接返回true
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在 permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
