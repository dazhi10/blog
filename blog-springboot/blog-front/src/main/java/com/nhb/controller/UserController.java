package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.User;
import com.nhb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表(SysUser)控制层
 * @author 大只
 * @since 2022-10-01 01:47:48
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("查看用户信息")
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @ApiOperation("更新个人信息接口")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}

