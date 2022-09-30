package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.Article;
import com.nhb.mapper.ArticleMapper;
import com.nhb.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章表(Article)表服务实现类
 *
 * @author 大只
 * @since 2022-09-30 19:07:34
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
