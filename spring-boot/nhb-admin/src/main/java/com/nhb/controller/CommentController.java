package com.nhb.controller;

import com.nhb.annotation.SystemLog;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.vo.PageVo;
import com.nhb.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 大只
 * @date 2022/10/20 10:59
 */
@RestController
@RequestMapping("/content/comment")
@Api(tags = "评论模块")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("查看评论列表")
    @PreAuthorize("@ps.hasPermission('comment:query')")
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, String content){
        return commentService.pageCommentList(pageNum,pageSize,content);
    }

    @ApiOperation("删除评论")
    @SystemLog(businessName = "删除评论")
    @PreAuthorize("@ps.hasPermission('comment:delete')")
    @DeleteMapping("/{ids}")
    public ResponseResult deleteComment(@PathVariable List<Long> ids){
        return commentService.deleteComment(ids);
    }
}
