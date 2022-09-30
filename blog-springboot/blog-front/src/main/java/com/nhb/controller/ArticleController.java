package com.nhb.controller;

import com.nhb.entity.domain.ResponseResult;
import com.nhb.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章表(Article)控制层
 *
 * @author 大只
 * @since 2022-09-30 19:07:29
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章模块")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("查看热门文章列表")
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @ApiOperation("查看文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页个数",required = true),
            @ApiImplicitParam(name = "categoryId",value = "分类id")
    })
    @GetMapping("/articleList")
    private ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId ){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @ApiOperation("文章详情")
    @ApiImplicitParam(name = "id",value = "文章id",required = true)
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

}

