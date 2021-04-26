package com.lx.yeb.vo;

import lombok.Data;

/**
 * @ClassName UserVO
 * @Description 前端传给后端接口对象
 * @Author lipan
 * @Date 2021/3/21 17:11
 * @Version 1.0
 */
@Data
public class UserVO{
    /**
     * 用户名
     * 密码
     * 头像
     * 权限
     * 验证码
     */
    private String  username;
    private String  password;
    private String  icon;
    private String  authority;
    private String  verifyCode;
}
