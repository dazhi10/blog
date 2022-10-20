package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.Comment;
import com.nhb.domain.vo.PageVo;

import java.util.List;


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

    /**
     * 查看评论列表
     *
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @param content  评论内容
     */
    ResponseResult<PageVo> pageCommentList(Integer pageNum, Integer pageSize, String content);

    /**
     * 删除评论
     * @param ids id集
     */
    ResponseResult deleteComment(List<Long> ids);
}
