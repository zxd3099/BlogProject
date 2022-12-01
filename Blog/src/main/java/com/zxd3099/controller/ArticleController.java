package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Article;
import com.zxd3099.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zxd3099
 * @create 2022-11-17-21:38
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章", description = "文章相关接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(Description = "获取热门文章列表")
    @ApiOperation(value = "热门文章列表", notes = "获取最多十篇热门文章")
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装返回
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @SystemLog(Description = "获取分类文章列表")
    @ApiOperation(value = "分类文章列表", notes = "分页查询对应分类下的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小"),
            @ApiImplicitParam(name = "categoryId",value = "分类Id")
    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(Description = "获取文章详情")
    @ApiOperation(value = "文章详情", notes = "文章详情页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    })
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(Description = "更新文章浏览量")
    @ApiOperation(value = "更新文章浏览量", notes = "更新文章浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    })
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        // 更新 redis 中对应 id 的浏览量
        return articleService.updateViewCount(id);
    }
}
