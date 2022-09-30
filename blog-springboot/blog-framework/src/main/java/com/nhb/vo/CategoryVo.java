package com.nhb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022/9/30 23:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private Long id;

    //分类名
    private String name;
}
