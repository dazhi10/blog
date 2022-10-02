package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)服务接口
 *
 * @author 大只
 * @since 2022-10-02 16:21:30
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户id查询角色信息
     * @param id 用户id
     * @return List<String>
     */
    List<String> selectRoleKeyByUserId(Long id);
}
