package com.nhb.service;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.User;

public interface BlogLoginService {
    /**
     * 用户登录
     * @param user 表单信息
     * @return ResponseResult
     */
    ResponseResult login(User user);

    /**
     * 退出登录
     * @return ResponseResult
     */
    ResponseResult logout();
}
