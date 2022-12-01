package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Role;
import com.zxd3099.domain.entity.UserRole;
import com.zxd3099.domain.vo.RoleVo;
import com.zxd3099.mapper.UserRoleMapper;
import com.zxd3099.service.RoleService;
import com.zxd3099.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-11-30 11:58:30
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    private RoleService roleService;

    @Override
    public List<RoleVo> selectRolesByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoleList = list(wrapper);

        List<RoleVo> collect = userRoleList.stream()
                .map(p -> roleService.getRole(p.getRoleId()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoleList = list(wrapper);

        List<Long> collect = userRoleList.stream().map(p -> p.getRoleId()).collect(Collectors.toList());
        return collect;
    }
}

