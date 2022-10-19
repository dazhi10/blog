package com.nhb.handler.exception;

import com.alibaba.fastjson.JSON;
import com.nhb.domain.ResponseResult;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.exception.SystemException;
import com.nhb.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 大只
 * @date 2022/10/1 11:47
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了异常!", e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常", e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理AccessDenied无权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult exceptionHandler(HttpServletResponse httpServletResponse, AccessDeniedException e) {
        log.error("不允许访问！原因是:", e);
        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }


}
