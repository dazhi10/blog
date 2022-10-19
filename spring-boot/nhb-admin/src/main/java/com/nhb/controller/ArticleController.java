package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.AddArticleDto;
import com.nhb.domain.dto.ArticleDto;
import com.nhb.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章表(Article)控制层
 *
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
    @PreAuthorize("@ps.hasPermission('article:add')")
    @PostMapping
    public ResponseResult saveArticle(@RequestBody AddArticleDto articleDto) {
        return articleService.saveArticle(articleDto);
    }

    @ApiOperation("查看文章列表")
    @PreAuthorize("@ps.hasPermission('article:query')")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, ArticleDto articleDto) {
        return articleService.articleList(pageNum, pageSize, articleDto);
    }

    @ApiOperation("查看单个文章")
    @PreAuthorize("@ps.hasPermission('article:query')")
    @GetMapping("/{id}")
    public ResponseResult articleById(@PathVariable String id) {
        return articleService.articleById(id);
    }

    @ApiOperation("修改文章")
    @PreAuthorize("@ps.hasPermission('article:put')")
    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleDto articleDto) {
        return articleService.updateArticle(articleDto);
    }

    @ApiOperation("删除文章")
    @PreAuthorize("@ps.hasPermission('article:delete')")
    @DeleteMapping("/{ids}")
    public ResponseResult deleteArticle(@PathVariable List<Long> ids) {
        return articleService.deleteArticle(ids);
    }
}

