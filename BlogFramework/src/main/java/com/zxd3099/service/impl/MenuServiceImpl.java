package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.constants.SystemConstants;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Menu;
import com.zxd3099.domain.entity.RoleMenu;
import com.zxd3099.domain.vo.MenuVo;
import com.zxd3099.domain.vo.RoleMenuTreeVo;
import com.zxd3099.mapper.MenuMapper;
import com.zxd3099.mapper.RoleMenuMapper;
import com.zxd3099.service.MenuService;
import com.zxd3099.service.RoleMenuService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-11-26 10:08:54
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 如果是管理员，返回所有的权限
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);

            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());

            return perms;
        }
        // 否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            // 如果是 获取所有符合要求的 Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 否则，获取当前用户所具有的 Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        // 构建tree
        // 先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus, 0L);
        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public ResponseResult listMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getStatus, status);
        wrapper.like(Menu::getMenuName, menuName);
        wrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);

        List<Menu> menuList = list(wrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        ResponseResult menu = getMenuById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult("修改菜单'写博文'失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        List<Menu> menus = selectAllMenuTree();
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, id);
        List<Long> menuList = roleMenuService.list(queryWrapper)
                .stream().map(p -> p.getMenuId())
                .collect(Collectors.toList());

        List<Menu> menus = selectAllMenuTree();
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        RoleMenuTreeVo roleMenuTreeVo = new RoleMenuTreeVo(menuVos, menuList);
        return ResponseResult.okResult(roleMenuTreeVo);
    }

    private List<Menu> selectAllMenuTree() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, 0L);
        List<Menu> menuList = list(queryWrapper);

        List<Menu> menus = builderMenuTree(menuList, 0L);
        return menus;
    }
}
