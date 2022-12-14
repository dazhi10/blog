package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.AddArticleDto;
import com.nhb.domain.dto.ArticleDto;
import com.nhb.domain.entity.Article;

import java.util.List;


/**
 * 文章表(Article)服务接口
 *
 * @author 大只
 * @since 2022-09-30 19:07:33
 */
public interface ArticleService extends IService<Article> {

    /**
     * 查看热门文章列表
     * @return ResponseResult
     */
    ResponseResult hotArticleList();

    /**
     * 查看文章列表
     * @param pageNum 页码
     * @param pageSize 每页个数
     * @param categoryId 分类id
     * @return ResponseResult
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleDto articleDto);

    /**
     * 查看文章详情
     * @param id 文章id
     * @return ResponseResult
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 更新文章浏览量
     * @param id 文章id
     * @return ResponseResult
     */
    ResponseResult updateViewCount(Long id);

    /**
     * 新增文章
     * @param articleDto 新增文章请求体
     * @return ResponseResult
     */
    ResponseResult saveArticle(AddArticleDto articleDto);

    /**
     * 查看单个文章
     * @param id 文章id
     * @return
     */
    ResponseResult articleById(String id);

    /**
     * 修改文章
     */
    ResponseResult updateArticle(ArticleDto articleDto);

    /**
     * 删除文章
     * @param ids 文章id集
     * @return
     */
    ResponseResult deleteArticle(List<Long> ids);
}
