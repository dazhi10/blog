package com.nhb.aspect;

import com.alibaba.fastjson.JSON;
import com.nhb.annotation.SystemLog;
import com.nhb.domain.entity.LoginUser;
import com.nhb.domain.entity.OperationLog;
import com.nhb.mapper.OperationLogMapper;
import com.nhb.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志AOP
 *
 * @author 大只
 * @date 2022/10/2 14:06
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private OperationLogMapper operationLogMapper;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.nhb.annotation.SystemLog)")
    public void pt() {

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        //操作日志对象
        OperationLog operationLog = new OperationLog();
        try {
            handleBefore(joinPoint, operationLog);
            ret = joinPoint.proceed();
            handleAfter(ret, operationLog);
            //将日志对象插入数据库
            operationLogMapper.insert(operationLog);

        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }

        return ret;
    }

    private void handleAfter(Object ret, OperationLog operationLog) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));

        //封装操作日志对象
        //操作人员，登录不用记录操作者
        if(!operationLog.getOperateMethod().equals("com.nhb.controller.LoginController.login")){
            LoginUser loginUser = SecurityUtils.getLoginUser();
            String username = loginUser.getUsername();
            operationLog.setOperateBy(username);
        }

        //返回结果
        operationLog.setResponseData(JSON.toJSONString(ret));

    }

    private void handleBefore(ProceedingJoinPoint joinPoint, OperationLog operationLog) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));


        //封装操作日志对象
        //操作描述
        operationLog.setOperateDescribe(systemLog.businessName());
        //请求URL
        operationLog.setUrl(String.valueOf(request.getRequestURL()));
        //请求方法
        operationLog.setRequestMethod(request.getMethod());
        //请求的类名
        String className = joinPoint.getSignature().getDeclaringTypeName() + "." + ((MethodSignature) joinPoint.getSignature()).getName();
        operationLog.setOperateMethod(className);
        //请求IP
        operationLog.setIp(request.getRemoteHost());
        //请求参数
        operationLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 获取被增强方法上的注解对象
     *
     * @param joinPoint 切入点
     * @return systemLog 返回结果
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;

    }
}
