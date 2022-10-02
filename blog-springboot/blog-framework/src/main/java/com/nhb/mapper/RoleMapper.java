package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author 大只
 * @since 2022-10-02 16:21:30
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long iuserIdd);
}


