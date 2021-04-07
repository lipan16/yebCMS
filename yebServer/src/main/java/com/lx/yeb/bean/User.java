package com.lx.yeb.bean;

import lombok.Data;

/**
 * @ClassName User
 * @Description 用户信息
 * @Author lipan
 * @Date 2021/3/18 10:01
 * @Version 1.0
 */
@Data
public class User{
    private Integer userid;
    private String  username;
    private String  password;
    private String  icon;
    private String  authority;
}
