package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.constants.SystemConstants;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Comment;
import com.zxd3099.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxd3099
 * @create 2022-11-19-16:26
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "评论相关接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(Description = "获取评论列表")
    @ApiOperation(value = "评论列表", notes = "获取一页评论，不需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章的id"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(Description = "发表评论")
    @ApiOperation(value = "对文章发表评论;对评论进行回复;在友链页面进行评论", notes = "用户需要登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comment",value = "发表的评论")
    })
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @PostMapping("/linkCommentList")
    @SystemLog(Description = "获取友链界面对应的评论列表")
    @ApiOperation(value = "友链界面对应的评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}
