package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.constants.SystemConstants;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.CategoryDto;
import com.zxd3099.domain.entity.Article;
import com.zxd3099.domain.entity.Category;
import com.zxd3099.domain.entity.User;
import com.zxd3099.domain.vo.CategoryVo;
import com.zxd3099.mapper.CategoryMapper;
import com.zxd3099.service.ArticleService;
import com.zxd3099.service.CategoryService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.SecurityUtils;
import org.apache.catalina.startup.Catalina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author zxd3099, ll
 * @since 2022-11-18 16:22:32
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表，状态为已经发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getArticleStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        // 获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        // 查询分类表
        List<Category> categories = listByIds(categoryIds);
        // 筛选正常的分类表
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getCategoryStatus()))
                .collect(Collectors.toList());
        // 封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getCategoryStatus, SystemConstants.STATUS_NORMAL);

        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult listCategory(Integer pageNumber, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status), Category::getCategoryStatus, status);
        wrapper.like(StringUtils.hasText(name), Category::getCategoryName, name);
        Page<Category> page = new Page<>(pageNumber, pageSize);
        page(page, wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        update(Wrappers.<Category>update()
                .lambda()
                .eq(Category::getId, id)
                .set(Category::getUpdateBy, SecurityUtils.getUserId())
                .set(Category::getUpdateTime, new Date())
                .set(Category::getDelFlag, 1));
        return ResponseResult.okResult();
    }
}

