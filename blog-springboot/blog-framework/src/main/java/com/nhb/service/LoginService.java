package com.nhb.service;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.User;

public interface LoginService {
    /**
     * 登录
     * @param user 用户对象
     * @return
     */
    ResponseResult login(User user);

    /**
     * 退出登录
     * @return
     */
    ResponseResult logout();
}
