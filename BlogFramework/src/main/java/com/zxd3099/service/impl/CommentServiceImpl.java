package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Comment;
import com.zxd3099.domain.vo.CommentVo;
import com.zxd3099.domain.vo.PageVo;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.handler.exception.SystemException;
import com.zxd3099.mapper.CommentMapper;
import com.zxd3099.service.CommentService;
import com.zxd3099.service.UserService;
import com.zxd3099.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-19 16:25:02
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 对 articleId 进行判断
        queryWrapper.eq(Comment::getArticleId, articleId);
        // 根评论的 rootId 为-1
        queryWrapper.eq(Comment::getRootId, -1);
        // 评论类型
        queryWrapper.eq(Comment::getType, commentType);

        // 分页查询
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        // 查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            // 查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            // 赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        // 评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 遍历commentVos集合
        for (CommentVo vo : commentVos) {
            // 通过createBy查询用户昵称并赋值
            vo.setUsername(userService.getById(vo.getCreateBy()).getNickName());
            // 通过toCommentId(不为-1)查询用户的昵称并赋值
            if (vo.getToCommentUserId() != -1) {
                vo.setToCommentUserName(userService.getById(vo.getToCommentUserId()).getNickName());
            }
        }
        return commentVos;
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

}

