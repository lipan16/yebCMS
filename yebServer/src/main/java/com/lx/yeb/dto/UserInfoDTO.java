package com.lx.yeb.dto;

import lombok.Data;

/**
 * @ClassName UserInfoDTO
 * @Description 返回用户信息对象
 * @Author lipan
 * @Date 2021/3/18 10:01
 * @Version 1.0
 */
@Data
public class UserInfoDTO{
    private String username;
    private String password;
    private String icon;
    private String authority;
    private String token;
}
