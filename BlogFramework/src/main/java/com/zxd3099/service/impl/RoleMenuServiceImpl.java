package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.domain.entity.RoleMenu;
import com.zxd3099.mapper.RoleMenuMapper;
import com.zxd3099.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-30 09:24:15
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

