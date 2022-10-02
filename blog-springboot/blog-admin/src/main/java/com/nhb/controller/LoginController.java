package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.LoginUser;
import com.nhb.domain.entity.Menu;
import com.nhb.domain.entity.User;
import com.nhb.domain.vo.AdminUserInfoVo;
import com.nhb.domain.vo.RoutersVo;
import com.nhb.domain.vo.UserInfoVo;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.exception.SystemException;
import com.nhb.service.LoginService;
import com.nhb.service.MenuService;
import com.nhb.service.RoleService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 大只
 * @date 2022/10/2 15:38
 */
@RestController
@Api(tags = "用户认证模块")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @ApiOperation("查看用户信息")
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @ApiOperation("获取路由列表")
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @ApiOperation("退出登录")
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }



}
