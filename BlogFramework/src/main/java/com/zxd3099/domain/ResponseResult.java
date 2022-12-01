package com.zxd3099.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zxd3099.domain.entity.Link;
import com.zxd3099.enums.AppHttpCodeEnum;
import lombok.Data;

import java.io.Serializable;

import static com.zxd3099.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @author zxd3099
 * @create 2022-11-17-23:06
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private String description;
    private T data;

    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data)
    {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data, String description)
    {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.description = description;
    }

    public ResponseResult(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseResult<T> resultByRows(int rows)
    {
        if (rows > 0)
        {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult();
    }

    public static <T> ResponseResult<T> errorResult(int code, String msg)
    {
        ResponseResult<T> result = new ResponseResult<>();
        return result.error(code, msg);
    }

    public static <T> ResponseResult<T> errorResult(int code, String msg, String description)
    {
        ResponseResult<T> result = new ResponseResult<>();
        return result.error(code, msg, description);
    }

    public static <T> ResponseResult<T> okResult()
    {
        return new ResponseResult<>();
    }

    public static <T> ResponseResult<T> okResult(int code, String msg)
    {
        ResponseResult<T> result = new ResponseResult<>();
        return result.ok(code, null, msg);
    }

    public static <T> ResponseResult<T> okResult(int code, String msg, String description)
    {
        ResponseResult<T> result = new ResponseResult<>();
        return result.ok(code, null, msg, description);
    }

    public static <T> ResponseResult<T> okResult(T data)
    {
        ResponseResult<T> result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getMsg());
        if (data != null)
        {
            result.setData(data);
        }
        return result;
    }

    public static <T> ResponseResult<T> okResult(T data, String description)
    {
        ResponseResult<T> result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getMsg(), description);
        if (data != null)
        {
            result.setData(data);
        }
        return result;
    }

    public static <T> ResponseResult<T> errorResult()
    {
        return setAppHttpCodeEnum(SYSTEM_ERROR, SYSTEM_ERROR.getMsg());
    }

    public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums)
    {
        return setAppHttpCodeEnum(enums, enums.getMsg());
    }

    public static <T> ResponseResult<T> errorResult(String msg)
    {
        return setAppHttpCodeEnum(SYSTEM_ERROR, msg);
    }

    public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums, String msg)
    {
        return setAppHttpCodeEnum(enums, msg);
    }

    public static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCodeEnum enums)
    {
        return okResult(enums.getCode(), enums.getMsg());
    }

    private static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg)
    {
        return okResult(enums.getCode(), msg);
    }

    private static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg, String description)
    {
        return okResult(enums.getCode(), msg, description);
    }

    public ResponseResult<T> error(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult<T> error(Integer code, String msg, String description)
    {
        this.code = code;
        this.msg = msg;
        this.description = description;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data)
    {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data, String msg)
    {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data, String msg, String description)
    {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.description = description;
        return this;
    }

}
