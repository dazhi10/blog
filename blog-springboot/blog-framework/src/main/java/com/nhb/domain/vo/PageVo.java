package com.nhb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 大只
 * @date 2022/10/1 00:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    private List rows;
    private Long total;
}