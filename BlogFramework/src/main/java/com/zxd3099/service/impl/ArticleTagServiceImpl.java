package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.domain.entity.ArticleTag;
import com.zxd3099.mapper.ArticleTagMapper;
import com.zxd3099.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-11-28 19:54:31
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

