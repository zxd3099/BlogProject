package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.AddArticleDto;
import com.zxd3099.domain.entity.Article;

/**
 * @author zxd3099
 * @create 2022-11-17-21:34
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto article);

    ResponseResult SearchArticle(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult getArticleById(Long id);

    ResponseResult updateArticle(Article article);

    ResponseResult deleteArticleById(Long id);
}
