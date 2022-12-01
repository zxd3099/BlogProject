package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-11-26 10:08:54
 */
public interface MenuService extends IService<Menu> {

    List<String> selectRoleKeyByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult listMenu(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeselect(Long id);
}

