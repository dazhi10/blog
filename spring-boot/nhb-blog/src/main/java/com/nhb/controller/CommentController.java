package com.nhb.controller;

import com.nhb.constant.SystemConstant;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.Comment;
import com.nhb.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论表(Comment)控制层
 *
 * @author 大只
 * @since 2022-10-01 13:31:10
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论模块")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation("查看文章评论列表")
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstant.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @ApiOperation("查看友链评论列表")
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstant.LINK_COMMENT, null, pageNum, pageSize);
    }

    @ApiOperation("发表评论")
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

}

