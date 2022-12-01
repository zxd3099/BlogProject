package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.constants.SystemConstants;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.AddArticleDto;
import com.zxd3099.domain.entity.Article;
import com.zxd3099.domain.entity.ArticleTag;
import com.zxd3099.domain.entity.Category;
import com.zxd3099.domain.vo.ArticleDetailVo;
import com.zxd3099.domain.vo.ArticleListVo;
import com.zxd3099.domain.vo.HotArticleVo;
import com.zxd3099.domain.vo.PageVo;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.mapper.ArticleMapper;
import com.zxd3099.service.ArticleService;
import com.zxd3099.service.ArticleTagService;
import com.zxd3099.service.CategoryService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.RedisCache;
import com.zxd3099.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zxd3099
 * @create 2022-11-17-21:34
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::getArticleStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序
        queryWrapper.orderByAsc(Article::getViewCount);
        // 最多只能查十条
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        // bean拷贝
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 如果有categoryId，查询时要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0, Article::getCategoryId, categoryId);
        // 文章是正式发布的
        queryWrapper.eq(Article::getArticleStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        // 查询category Name
        List<Article> articles =  page.getRecords();
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getCategoryName()))
                .collect(Collectors.toList());

        // 封装categoryName
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据id查询文章
        Article article = getById(id);
        // 从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        // 转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getCategoryName());
        }
        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新 redis 中对应 id 的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult SearchArticle(Integer pageNum, Integer pageSize, String title, String summary) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getTitle, title);
        queryWrapper.like(Article::getSummary, summary);

        // 文章是正式发布的
        queryWrapper.eq(Article::getArticleStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        // 查询category Name
        List<Article> articles =  page.getRecords();
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getCategoryName()))
                .collect(Collectors.toList());

        // 封装categoryName
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleById(Long id) {
        Article article = getById(id);
        return ResponseResult.okResult(article);
    }

    @Override
    public ResponseResult updateArticle(Article article) {
        updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticleById(Long id) {
        // 数据库中该条数据还是存在的，只是修改了逻辑删除字段的值
        boolean result = update(Wrappers.<Article>update()
                .lambda()
                .eq(Article::getId, id)
                .set(Article::getUpdateBy, SecurityUtils.getUserId())
                .set(Article::getUpdateTime, new Date())
                .set(Article::getDelFlag, 1));
        if (result) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SORRY_NOT_FOUND);
        }
    }
}
