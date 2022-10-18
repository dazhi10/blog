package com.nhb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022/10/3 19:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTagDto {
    //备注
    private String remark;
    //标签名
    private String name;
}
