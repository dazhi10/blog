package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.entity.Category;
import com.nhb.entity.domain.ResponseResult;


/**
 * 分类表(Category)服务接口
 *
 * @author 大只
 * @since 2022-09-30 23:23:37
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查看分类列表
     * @return ResponseResult
     */
    ResponseResult getCategoryList();
}
