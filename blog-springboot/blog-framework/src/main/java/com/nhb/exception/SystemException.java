package com.nhb.exception;

import com.nhb.enums.AppHttpCodeEnum;

/**
 * @author 大只
 * @date 2022/10/1 11:45
 */
public class SystemException extends RuntimeException{
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
