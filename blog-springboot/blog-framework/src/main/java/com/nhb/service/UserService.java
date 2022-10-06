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

    /**
     * 更新个人信息接口
     * @param user 用户对象
     * @return ResponseResult
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 用户注册
     * @param user 用户对象
     * @return
     */
    ResponseResult register(User user);

    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    ResponseResult addUser(User user);

    void updateUser(User user);
}
