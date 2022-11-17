package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.constant.SystemConstant;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.Comment;
import com.nhb.domain.vo.CommentVo;
import com.nhb.domain.vo.PageVo;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.exception.SystemException;
import com.nhb.mapper.CommentMapper;
import com.nhb.service.CommentService;
import com.nhb.service.UserService;
import com.nhb.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author 大只
 * @since 2022-10-01 13:31:11
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstant.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        //根评论rootId为-1
        queryWrapper.eq(Comment::getRootId, -1);
        //评论类型
        queryWrapper.eq(Comment::getType, commentType);
        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {



        //评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }

        //TODO AI识别敏感词


        save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> pageCommentList(Integer pageNum, Integer pageSize, String content) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(content), Comment::getContent, content);
        Page<Comment> commentPage = new Page<>(pageNum, pageSize);
        page(commentPage, queryWrapper);
        PageVo pageVo = new PageVo(commentPage.getRecords(), commentPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult deleteComment(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    /**
     * 根据评论的id查询所对应的子评论集合
     *
     * @param id 根评论的id
     * @return List<CommentVo>
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(queryWrapper);
        return toCommentVoList(commentList);
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        List<CommentVo> collect = commentVos.stream()
                //通过createBy查询用户的昵称并赋值
                .map(commentVo -> commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName()))
                .map(commentVo -> commentVo.setAvatar(userService.getById(commentVo.getCreateBy()).getAvatar()))
                .collect(Collectors.toList());

        //这条评论的回复者
        for (CommentVo commentVo : collect) {
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }

        return collect;
    }
}
