package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author 大只
 * @since 2022-10-02 16:17:03
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}


