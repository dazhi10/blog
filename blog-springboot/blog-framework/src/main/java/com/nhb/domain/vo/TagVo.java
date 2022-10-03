package com.nhb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022/10/3 16:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;

    //标签名
    private String name;

    //备注
    private String remark;

}
