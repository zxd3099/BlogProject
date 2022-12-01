package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxd3099
 * @create 2022-11-18-16:28
 */
@RestController
@RequestMapping("/category")
@Api(tags = "类别", description = "类别相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(Description = "获取分类列表")
    @ApiOperation(value = "获取分类列表", notes = "获取分类列表")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }
}
