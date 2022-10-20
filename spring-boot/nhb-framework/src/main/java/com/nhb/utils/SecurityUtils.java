package com.nhb.utils;

import com.nhb.domain.entity.LoginUser;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.exception.SystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @author 大只
 * @date 2022/10/1 02:12
 */
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
