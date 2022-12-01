package com.zxd3099.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zxd3099
 * @create 2022-11-17-23:17
 */
@AllArgsConstructor
@Getter
public enum AppHttpCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 需要登录后操作
     */
    NEED_LOGIN(401, "需要登录后操作"),
    /**
     * 无权限操作
     */
    NO_OPERATOR_AUTH(403, "无权限操作"),
    /**
     * 找不到资源
     */
    SORRY_NOT_FOUND(404, "找不到资源"),
    /**
     * 出现错误
     */
    SYSTEM_ERROR(500, "系统错误"),
    /**
     * 请求参数错误
     */
    PARAMETER_ERROR(400, "请求参数错误"),
    /**
     * 用户名已存在
     */
    USERNAME_EXIST(501, "用户名已存在"),
    /**
     * 手机号已存在
     */
    PHONE_NUMBER_EXIST(502, "手机号已存在"),
    /**
     * 邮箱已存在
     */
    EMAIL_EXIST(503, "邮箱已存在"),
    /**
     * 必需填写用户名
     */
    REQUIRE_USERNAME(504, "必需填写用户名"),
    /**
     * 用户名或密码错误
     */
    LOGIN_ERROR(505, "用户名或密码错误"),
    /**
     * 请不要频繁操作
     */
    LIMIT_ERROR(506, "请不要频繁操作"),
    /**
     * 内容不能为空
     */
    CONTENT_NOT_NULL(507, "内容不能为空"),
    /**
     * 图片格式错误
     */
    FILE_TYPE_ERROR(508, "文件类型错误,请上传JPG/JPEG/PNG/BMP文件"),
    /**
     * 用户名不能为空
     */
    USERNAME_NOT_NULL(509, "用户名不能为空"),
    /**
     * 昵称不能为空
     */
    NICKNAME_NOT_NULL(510, "昵称不能为空"),
    /**
     * 密码不能为空
     */
    PASSWORD_NOT_NULL(511, "密码不能为空"),
    /**
     * 邮箱不能为空
     */
    EMAIL_NOT_NULL(512, "邮箱不能为空"),
    /**
     * 昵称已存在
     */
    NICKNAME_EXIST(513, "昵称已存在");
    /**
     * 状态码
     */
    int code;
    /**
     * 信息提示
     */
    String msg;
}
