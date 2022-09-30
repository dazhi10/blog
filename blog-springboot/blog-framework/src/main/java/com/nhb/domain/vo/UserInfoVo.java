package com.nhb.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 大只
 * @date 2022/10/1 02:21
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}


