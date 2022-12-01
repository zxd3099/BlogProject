package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-11-19 16:25:02
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

