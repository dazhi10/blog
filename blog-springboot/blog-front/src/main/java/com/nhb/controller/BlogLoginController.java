package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.User;
import com.nhb.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 大只
 * @date 2022/10/1 01:50
 */
@RestController
@Api(tags = "用户模块")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return blogLoginService.login(user);
    }
}
