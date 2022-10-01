package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.User;
import com.nhb.domain.vo.UserInfoVo;
import com.nhb.mapper.UserMapper;
import com.nhb.service.UserService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author 大只
 * @since 2022-10-01 01:47:48
 */
@Service("sysUserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}
