package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author 大只
 * @since 2022-09-30 19:07:30
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 修改文章浏览量
     */
    void updateArticleView(@Param("id") Long id, @Param("viewCount") Long viewCount);
}


