package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.constants.SystemConstants;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.RoleDto;
import com.zxd3099.domain.entity.Article;
import com.zxd3099.domain.entity.Role;
import com.zxd3099.domain.entity.RoleMenu;
import com.zxd3099.domain.vo.RoleVo;
import com.zxd3099.mapper.RoleMapper;
import com.zxd3099.mapper.RoleMenuMapper;
import com.zxd3099.service.RoleMenuService;
import com.zxd3099.service.RoleService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-11-26 10:14:49
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员，如果是返回集合中只需要有admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        // 否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult listRole(Integer pageNumber, Integer pageSize, String roleName, String status) {
        // 查询条件
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, status);
        queryWrapper.like(Role::getRoleName, roleName);
        queryWrapper.orderByAsc(Role::getRoleSort);

        // 分页查询
        Page<Role> page = new Page<>(pageNumber, pageSize);
        page(page, queryWrapper);

        List<Role> roleList = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roleList, RoleVo.class);
        return ResponseResult.okResult(roleVos);
    }

    @Override
    public ResponseResult changeStatus(Long roleId, String status) {
        update(Wrappers.<Role>update()
                .lambda()
                .eq(Role::getId, roleId)
                .set(Role::getUpdateBy, SecurityUtils.getUserId())
                .set(Role::getUpdateTime, new Date())
                .set(Role::getStatus, status));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);

        List<RoleMenu> roleMenuList = roleDto.getMenuIds().stream()
                .map(p -> new RoleMenu(role.getId(), p))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
        return ResponseResult.okResult();
    }

    @Override
    public RoleVo getRole(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return roleVo;
    }

    @Override
    public ResponseResult updateRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);

        List<RoleMenu> roleMenuList = roleDto.getMenuIds().stream()
                .map(p -> new RoleMenu(role.getId(), p))
                .collect(Collectors.toList());
        roleMenuService.saveOrUpdateBatch(roleMenuList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        update(Wrappers.<Role>update()
                .lambda()
                .eq(Role::getId, id)
                .set(Role::getUpdateBy, SecurityUtils.getUserId())
                .set(Role::getUpdateTime, new Date())
                .set(Role::getDelFlag, 1));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);

        List<Role> roles = list(queryWrapper);
        return ResponseResult.okResult(roles);
    }
}

