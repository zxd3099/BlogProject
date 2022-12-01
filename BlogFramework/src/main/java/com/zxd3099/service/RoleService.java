package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.RoleDto;
import com.zxd3099.domain.entity.Role;
import com.zxd3099.domain.vo.RoleVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-11-26 10:14:49
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listRole(Integer pageNumber, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(Long roleId, String status);

    ResponseResult addRole(RoleDto roleDto);

    RoleVo getRole(Long id);

    ResponseResult updateRole(RoleDto roleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();
}

