package com.nhb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 大只
 * @date 2022/10/6 19:56
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserStatusDto {
    private Long userId;
    private String status;
}
