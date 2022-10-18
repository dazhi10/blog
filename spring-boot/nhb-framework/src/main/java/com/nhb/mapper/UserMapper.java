package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author 大只
 * @since 2022-10-01 01:47:48
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}


