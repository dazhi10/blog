package com.nhb.service;

import com.nhb.domain.entity.User;
import com.nhb.domain.ResponseResult;

public interface BlogLoginService {
    ResponseResult login(User user);
}
