package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.constant.SystemConstant;
import com.nhb.domain.entity.Article;
import com.nhb.domain.entity.Category;
import com.nhb.domain.ResponseResult;
import com.nhb.mapper.ArticleMapper;
import com.nhb.service.ArticleService;
import com.nhb.service.CategoryService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.domain.vo.ArticleDetailVo;
import com.nhb.domain.vo.ArticleListVo;
import com.nhb.domain.vo.HotArticleVo;
import com.nhb.domain.vo.PageVo;
import com.nhb.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author 大只
 * @since 2022-09-30 19:07:34
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {

        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询10条
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);
        //封装vo
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(page.getRecords(), HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //条件查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId查询时就要传入
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //文章状态是正式发布
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序，让置顶的文章放在前面
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查找
        Page<Article> page = new Page(pageNum, pageSize);
        page(page, queryWrapper);
        List<Article> articleList = page.getRecords();
        //文章对应的查询分类名
        List<Article> collect = articleList.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        for (Article article : collect) {
            //从redis中获取viewCount
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        //封装vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(collect, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //封装vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if(Objects.nonNull(category)){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新浏览量时去更新redis中的数据
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
}
