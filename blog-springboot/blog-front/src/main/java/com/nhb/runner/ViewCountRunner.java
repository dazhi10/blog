package com.nhb.runner;

import com.nhb.domain.entity.Article;
import com.nhb.mapper.ArticleMapper;
import com.nhb.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 大只
 * @date 2022/10/2 14:28
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;


    @Override
    public void run(String... args) throws Exception {
        //在应用启动时把博客文章的浏览量存储到redis中
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articleList.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));
        //存储redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
