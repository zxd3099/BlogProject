package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.CategoryDto;
import com.zxd3099.domain.entity.Category;
import com.zxd3099.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-11-18 16:22:32
 */
public interface CategoryService extends IService<Category> {

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult listCategory(Integer pageNumber, Integer pageSize, String name, String status);

    ResponseResult getCategoryById(Long id);

    ResponseResult deleteCategory(Long id);
}

