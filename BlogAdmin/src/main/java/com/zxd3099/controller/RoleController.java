package com.zxd3099.controller;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.RoleDto;
import com.zxd3099.domain.vo.RoleVo;
import com.zxd3099.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxd3099
 * @create 2022-11-28-23:16
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult listRole(Integer pageNumber, Integer pageSize, String roleName, String status) {
        return roleService.listRole(pageNumber, pageSize, roleName, status);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(Long roleId, String status) {
        return roleService.changeStatus(roleId, status);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto roleDto) {
        return roleService.addRole(roleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable("id") Long id) {
        RoleVo role = roleService.getRole(id);
        return ResponseResult.okResult(role);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleDto roleDto) {
        return roleService.updateRole(roleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id) {
        return roleService.deleteRole(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }
}
