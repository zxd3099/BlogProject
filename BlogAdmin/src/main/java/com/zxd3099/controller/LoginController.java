package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.LoginUser;
import com.zxd3099.domain.entity.Menu;
import com.zxd3099.domain.entity.User;
import com.zxd3099.domain.vo.AdminUserInfoVo;
import com.zxd3099.domain.vo.RoutersVo;
import com.zxd3099.domain.vo.UserInfoVo;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.handler.exception.SystemException;
import com.zxd3099.service.LoginService;
import com.zxd3099.service.MenuService;
import com.zxd3099.service.RoleService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author zxd3099
 * @create 2022-11-26-9:16
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @SystemLog(Description = "用户后台登录")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示，必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("getInfo")
    @SystemLog(Description = "得到权限信息")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 根据用户 id 查询权限信息
        List<String> perms = menuService.selectRoleKeyByUserId(loginUser.getUser().getId());

        // 根据用户 id 查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        // 获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    @SystemLog(Description = "返回用户所能访问的菜单数据")
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        // 查询menu，结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        // 封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    @SystemLog(Description = "用户退出登录")
    public ResponseResult logout() {
        return loginService.logout();
    }
}
