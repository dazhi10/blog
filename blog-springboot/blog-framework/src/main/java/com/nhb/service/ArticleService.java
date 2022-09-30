package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.entity.Article;
import com.nhb.domain.ResponseResult;


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

    /**
     * 查看文章详情
     * @param id 文章id
     * @return ResponseResult
     */
    ResponseResult getArticleDetail(Long id);
}
