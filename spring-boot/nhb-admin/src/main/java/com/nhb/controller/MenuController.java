package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.Menu;
import com.nhb.domain.vo.MenuTreeVo;
import com.nhb.domain.vo.RoleMenuTreeSelectVo;
import com.nhb.service.MenuService;
import com.nhb.utils.SystemConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限表(Menu)控制层
 * @author 大只
 * @since 2022-10-02 16:17:03
 */
@RestController
@RequestMapping("/system/menu")
@Api(tags = "菜单模块")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("查看菜单列表")
    @GetMapping("/list")
    public ResponseResult menuList(@RequestParam(required = false) String menuName,@RequestParam(required = false) String status){
        return  menuService.menuList(menuName,status);
    }

    @ApiOperation("新增菜单")
    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @ApiOperation("根据id获取菜单")
    @GetMapping(value = "/{menuId}")
    public ResponseResult getInfo(@PathVariable Long menuId) {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    @ApiOperation("修改菜单")
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/{menuId}")
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChild(menuId)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }

    @ApiOperation("获取菜单下拉树列表")
    @GetMapping("/tree")
    public ResponseResult tree() {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    @ApiOperation("加载对应角色菜单列表树")
    @GetMapping(value = "/roleMenuTree/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }

}

