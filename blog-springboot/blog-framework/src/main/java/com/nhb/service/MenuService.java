package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)服务接口
 *
 * @author 大只
 * @since 2022-10-02 16:17:03
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据用户id查询权限信息
     * @param id 用户id
     * @return List<String>
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 根据用户id获取路由菜单
     * @param userId 用户id
     * @return List<Menu>
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
