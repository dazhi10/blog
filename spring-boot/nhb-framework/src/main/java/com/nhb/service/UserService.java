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
     */
    ResponseResult userInfo();

    /**
     * 更新个人信息接口
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 用户注册
     */
    ResponseResult register(User user);

    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    ResponseResult addUser(User user);

    void updateUser(User user);


    /**
     * 校验用户名是否存在
     */
    boolean checkUserNameUnique(String userName);

    /**
     * 发送邮箱验证码
     */
    ResponseResult sendCode(String gmail);
}
