package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.constant.SystemConstant;
import com.nhb.domain.entity.Article;
import com.nhb.domain.entity.Category;
import com.nhb.domain.ResponseResult;
import com.nhb.mapper.CategoryMapper;
import com.nhb.service.ArticleService;
import com.nhb.service.CategoryService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.domain.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author 大只
 * @since 2022-09-30 23:23:37
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表，状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        List<Category> collect = categories.stream()
                //用stream过滤状态正常的分类
                .filter(category -> SystemConstant.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(collect, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
