package com.zxd3099.handler.exception;

import com.zxd3099.enums.AppHttpCodeEnum;

/**
 * @author zxd3099
 * @create 2022-11-19-15:45
 * @description 系统异常类
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
