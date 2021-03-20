package com.lx.yeb.dto;

import com.lx.yeb.bean.User;
import lombok.Data;

/**
 * @ClassName UserInfoDTO
 * @Description 返回用户信息对象
 * @Author lipan
 * @Date 2021/3/18 10:01
 * @Version 1.0
 */
@Data
public class UserInfoDTO extends User{
    private String token;
}
