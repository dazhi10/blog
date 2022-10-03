package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类表(Category)控制层
 * @author 大只
 * @since 2022-10-03 17:18:15
 */
@RestController
@RequestMapping("/content/category")
@Api(tags = "分类模块")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查看所有分类列表")
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }


}

