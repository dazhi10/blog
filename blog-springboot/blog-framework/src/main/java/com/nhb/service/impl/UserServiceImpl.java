package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.domain.entity.User;
import com.nhb.mapper.UserMapper;
import com.nhb.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author 大只
 * @since 2022-10-01 01:47:48
 */
@Service("sysUserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
