package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.constant.SystemConstant;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.AddArticleDto;
import com.nhb.domain.dto.ArticleDto;
import com.nhb.domain.entity.Article;
import com.nhb.domain.entity.ArticleTag;
import com.nhb.domain.entity.Category;
import com.nhb.domain.vo.*;
import com.nhb.mapper.ArticleMapper;
import com.nhb.mapper.ArticleTagMapper;
import com.nhb.service.ArticleService;
import com.nhb.service.ArticleTagService;
import com.nhb.service.CategoryService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleTagMapper articleTagMapper;


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

        for (Article article : page.getRecords()) {
            //从redis中获取viewCount
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            if(!Objects.isNull(viewCount)){
                article.setViewCount(viewCount.longValue());
            }
        }
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
        for (Article article : articleList) {
            Category category = categoryService.getById(article.getCategoryId());
            if(!Objects.isNull(category)){
                article.setCategoryName(category.getName());
            }
        }

        for (Article article : articleList) {
            //从redis中获取viewCount
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            if(!Objects.isNull(viewCount)){
                article.setViewCount(viewCount.longValue());
            }
        }
        //封装vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo(articleListVos, page.getTotal()));
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleDto articleDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        queryWrapper.like(StringUtils.hasText(articleDto.getTitle()), Article::getTitle, articleDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleDto.getSummary()), Article::getSummary, articleDto.getSummary());
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
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
        if (Objects.nonNull(category)) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新浏览量时去更新redis中的数据
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult saveArticle(AddArticleDto articleDto) {
        //添加文章
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        //浏览量添加到redis中
        Map<String, Integer> viewCountMap = new HashMap<>();
        viewCountMap.put(article.getId().toString(),0);

        //添加redis
        redisCache.setCacheMap("article:viewCount",viewCountMap);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加文章和标签的关联
        articleTagService.saveBatch(articleTags);


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult articleById(String id) {
        ArticleVo articleVo = BeanCopyUtils.copyBean(getById(id), ArticleVo.class);
        articleVo.setTags(articleTagMapper.getArticleTagList(articleVo.getId()));
        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateArticle(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //更新文章
        updateById(article);

        //先删除文章关联标签
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleDto.getId());
        articleTagMapper.delete(queryWrapper);

        //再插入文章关联标签
        articleDto.getTags().forEach(tagId -> articleTagMapper.insert(new ArticleTag(articleDto.getId(), tagId)));

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }
}
