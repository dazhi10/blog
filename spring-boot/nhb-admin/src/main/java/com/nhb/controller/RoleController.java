package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.ChangeRoleStatusDto;
import com.nhb.domain.entity.Role;
import com.nhb.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息表(Role)控制层
 *
 * @author 大只
 * @since 2022-10-02 16:21:30
 */
@RestController
@RequestMapping("system/role")
@Api(tags = "角色模块")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("查看角色列表")
    @GetMapping("/list")
    public ResponseResult listRole(Integer pageNum, Integer pageSize, @RequestParam(required = false) String roleName, @RequestParam(required = false) String status) {
        return roleService.listRole(pageNum, pageSize, roleName, status);
    }

    @ApiOperation("根据id获取角色")
    @GetMapping(value = "/{roleId}")
    public ResponseResult getInfo(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        return ResponseResult.okResult(role);
    }

    @ApiOperation("改变角色状态")
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto) {
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        return ResponseResult.okResult(roleService.updateById(role));
    }

    @ApiOperation("新增角色")
    @PostMapping
    public ResponseResult add(@RequestBody Role role) {
        roleService.insertRole(role);
        return ResponseResult.okResult();
    }

    @ApiOperation("修改角色")
    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        List<Role> roles = roleService.selectRoleAll();
        return ResponseResult.okResult(roles);
    }



}
