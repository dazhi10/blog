package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.User;


/**
 * 用户表(SysUser)服务接口
 *
 * @author 大只
 * @since 2022-10-01 01:47:48
 */
public interface UserService extends IService<User> {
    /**
     * 查看用户信息
     * @return ResponseResult
     */
    ResponseResult userInfo();
}
