package com.nhb.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author 大只
 * @since 2022-10-03 17:11:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_tag")
public class ArticleTag  {
    //文章id
    private Long articleId;
    //标签id
    private Long tagId;




}


