package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.AddArticleDto;
import com.nhb.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章表(Article)控制层
 * @author 大只
 * @since 2022-10-03 19:42:30
 */
@RestController
@RequestMapping("/content/article")
@Api(tags = "文章模块")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("新增文章")
    @PostMapping
    public ResponseResult saveArticle(@RequestBody AddArticleDto articleDto){
        return articleService.saveArticle(articleDto);
    }
}

