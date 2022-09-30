package com.nhb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 大只
 * @date 2022/10/1 00:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {

    private Long id;

    //标题
    private String title;
    //文章内容
    private String content;

    //所属分类id
    private Long categoryId;

    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;

    private Date createTime;


    private String categoryName;
}
