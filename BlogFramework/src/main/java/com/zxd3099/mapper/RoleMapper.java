package com.zxd3099.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxd3099.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-26 10:14:49
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

