package com.nhb.job;

import com.nhb.domain.entity.Article;
import com.nhb.mapper.ArticleMapper;
import com.nhb.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 大只
 * @date 2022/10/2 14:39
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

   @Autowired
   private ArticleMapper articleMapper;

    //"0 */5 * * * ?"
    @Scheduled(cron = "0/30 * * * * ?")
    public void updateViewCount() {
        //定时任务每隔10分钟把Redis中的浏览量更新到数据库中
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        for (Article article : articles) {
            articleMapper.updateArticleView(article.getId(),article.getViewCount());
        }
    }
}
