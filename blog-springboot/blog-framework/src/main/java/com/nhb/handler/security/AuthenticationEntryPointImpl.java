package com.nhb.handler.security;

import com.alibaba.fastjson.JSON;
import com.nhb.domain.ResponseResult;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 * @author 大只
 * @date 2022/10/1 11:38
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = null;
        if(e instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if(e instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
