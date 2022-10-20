package com.nhb.controller;

import com.nhb.annotation.SystemLog;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.ChangeUserStatusDto;
import com.nhb.domain.entity.Role;
import com.nhb.domain.entity.User;
import com.nhb.domain.vo.UserInfoAndRoleIdsVo;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.exception.SystemException;
import com.nhb.service.RoleService;
import com.nhb.service.UserService;
import com.nhb.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 大只
 * @date 2022/10/6 18:11
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ApiOperation("查看用户列表")
    @PreAuthorize("@ps.hasPermission('sys:user:query')")
    @GetMapping("/list")
    public ResponseResult list(User user, Integer pageNum, Integer pageSize) {
        return userService.selectUserPage(user, pageNum, pageSize);
    }

    @ApiOperation(" 新增用户")
    @SystemLog(businessName = "新增用户")
    @PreAuthorize("@ps.hasPermission('sys:user:add')")
    @PostMapping
    public ResponseResult add(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return userService.addUser(user);
    }

    @ApiOperation("删除用户")
    @SystemLog(businessName = "删除用户")
    @PreAuthorize("@ps.hasPermission('sys:user:delete')")
    @DeleteMapping("/{userIds}")
    public ResponseResult remove(@PathVariable List<Long> userIds) {
        if (userIds.contains(SecurityUtils.getUserId())) {
            return ResponseResult.errorResult(500, "不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return ResponseResult.okResult();
    }

    @ApiOperation("根据id获取用户")
    @PreAuthorize("@ps.hasPermission('sys:user:query')")
    @GetMapping(value = {"/{userId}"})
    public ResponseResult getUserInfoAndRoleIds(@PathVariable(value = "userId") Long userId) {
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(userId);
        //当前用户所具有的角色id列表
        List<Long> roleIds = roleService.selectRoleIdByUserId(userId);

        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user, roles, roleIds);
        return ResponseResult.okResult(vo);
    }

    @ApiOperation("修改用户")
    @SystemLog(businessName = "修改用户")
    @PreAuthorize("@ps.hasPermission('sys:user:put')")
    @PutMapping
    public ResponseResult edit(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.okResult();
    }

    @ApiOperation("修改用户状态")
    @SystemLog(businessName = "修改用户")
    @PreAuthorize("@ps.hasPermission('sys:user:put')")
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeUserStatusDto changeUserStatusDto) {
        if (SecurityUtils.getUserId().equals(changeUserStatusDto.getUserId())) {
            return ResponseResult.errorResult(500, "不能修改当前你正在使用的用户");
        }
        User user = new User();
        user.setId(changeUserStatusDto.getUserId());
        user.setStatus(changeUserStatusDto.getStatus());
        return ResponseResult.okResult(userService.updateById(user));
    }

}
