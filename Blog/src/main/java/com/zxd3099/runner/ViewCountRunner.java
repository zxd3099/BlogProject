package com.zxd3099.runner;

import com.zxd3099.domain.entity.Article;
import com.zxd3099.mapper.ArticleMapper;
import com.zxd3099.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zxd3099
 * @create 2022-11-23-19:54
 * @description 在SpringBoot应用启动时进行一些初始化操作可以选择使用CommandLineRunner来进行处理
 * 在应用启动时把博客的浏览量存储到redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息  id  viewCounter
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        // 存储到 Redis 中
        redisCache.setCacheMap("article:viewCount", viewCountMap);
    }
}
