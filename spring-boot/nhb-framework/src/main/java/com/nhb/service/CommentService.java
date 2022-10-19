package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.Comment;


/**
 * 评论表(Comment)服务接口
 *
 * @author 大只
 * @since 2022-10-01 13:31:11
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查看评论列表
     *
     * @param articleId 文章id
     * @param pageNum   页码
     * @param pageSize  每页个数
     * @return ResponseResult
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);


    /**
     * 发表评论
     *
     * @param comment 评论信息
     * @return ResponseResult
     */
    ResponseResult addComment(Comment comment);
}
