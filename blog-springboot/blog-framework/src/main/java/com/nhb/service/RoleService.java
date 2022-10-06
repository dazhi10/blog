package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.ResponseResult;
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

    /**
     * 查看角色列表
     * @param pageNum 页码
     * @param pageSize 每页个数
     * @param roleName 角色名
     * @param status 状态
     * @return
     */
    ResponseResult listRole(Integer pageNum, Integer pageSize, String roleName, String status);

    void insertRole(Role role);

    void updateRole(Role role);

    List<Role> selectRoleAll();

    List<Long> selectRoleIdByUserId(Long userId);
}
