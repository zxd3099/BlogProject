package com.zxd3099.controller;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.AddArticleDto;
import com.zxd3099.domain.entity.Article;
import com.zxd3099.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxd3099
 * @create 2022-11-28-19:51
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/addArticle")
    public ResponseResult addArticle(@RequestBody AddArticleDto article) {
        return articleService.addArticle(article);
    }

    @PostMapping("/list")
    public ResponseResult SearchArticle(Integer pageNum, Integer pageSize, String title, String summary) {
        return articleService.SearchArticle(pageNum, pageSize, title, summary);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id) {
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody Article article) {
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticleById(@PathVariable("id") Long id) {
        return articleService.deleteArticleById(id);
    }
}
