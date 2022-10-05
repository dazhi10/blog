package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author 大只
 * @since 2022-10-03 17:11:41
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    /**
     * 查询文章对应的标签id
     * @param id 文章id
     * @return
     */
    List<Long> getArticleTagList(Long id);
}


