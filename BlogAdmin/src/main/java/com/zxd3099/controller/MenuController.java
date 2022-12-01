package com.zxd3099.controller;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.MenuDto;
import com.zxd3099.domain.entity.Menu;
import com.zxd3099.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxd3099
 * @create 2022-11-28-21:58
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult listMenu(String status, String menuName) {
        return menuService.listMenu(status, menuName);
    }

    @PostMapping("/addMenu")
    public ResponseResult addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id) {
        return menuService.getMenuById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @GetMapping("/treeSelect")
    public ResponseResult treeSelect() {
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable("id") Long id) {
        return menuService.roleMenuTreeselect(id);
    }
}
