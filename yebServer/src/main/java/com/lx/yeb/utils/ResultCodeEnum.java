package com.lx.yeb.utils;

/**
 * @ClassName ResultCodeEnum
 * @Description 返回状态码枚举表
 * @Author lipan
 * @Date 2021/3/18 9:56
 * @Version 1.0
 */
public enum ResultCodeEnum{
    SUCCESS(200, "success"),
    SUCCESS_LOGOUT(200, "注销成功"),
    USER_EXIST(1001, "用户已存在"),
    USERNAME_NOT_EXISTS(1002, "用户名不存在"),
    PASSWORD_ERROR(1003, "密码错误"),
    ADMIN_NOT_BE_NULL(1004, "用户名密码不能为空"),
    SERVER_ERROR(1005, "服务器错误"),
    USER_NOT_LOGIN(1006, "用户未登录"),
    TOKEN_ERROR(1007, "token无效"),
    TOKEN_EXPIRED(1007, "token已过期"),
    TOKEN_UNSUPPORTED(1008,"token格式错误"),
    TOKEN_MALFORMED(1009,"token没有被正确构造"),
    TOKEN_SIGNATURE(1010, "token签名失败"),
    TOKEN_ILLEGAL_ARGUMENT(1011,"token非法参数异常"),
    TOKEN_REFRESH_FAILED(1012, "token刷新失败"),

    VERIFICATION_BE_REQUIRED(1013, "验证码不能为空"),
    VERIFICATION_EXPIRED(1014, "验证码已过期，请刷新验证码"),
    VERIFICATION_FAILED(1015, "验证码校验失败，请重新输入"),
    NO_PERMISSION(1016, "没有权限"),
    FAILURE(9999, "fail");

    // 结果状态码
    private Integer flags;
    // 结果消息
    private String  info;

    ResultCodeEnum(Integer flags, String info){
        this.flags = flags;
        this.info = info;
    }

    public Integer getFlags(){
        return flags;
    }

    public String getInfo(){
        return info;
    }
}
