package com.lx.yeb.service;

import com.lx.yeb.bean.User;

/**
 * @ClassName LoginService
 * @Description 登录方法接口
 * @Author lipan
 * @Date 2021/3/17 11:44
 * @Version 1.0
 */
public interface LoginService{

    /**
     * fetch 验证登录
     *
     * @param user
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/17 13:31
     */
    String verifyLogin(User user);

    /**
     * fetch 刷新token
     * @author lipan
     * @date 2021/3/24 13:52
     * @param user
     * @return java.lang.String
     */
    String refreshToken(User user);

    /**
     * fetch 返回当前用户的导航栏
     * @author lipan
     * @date 2021/3/29 16:23
     * @param user
     * @return java.lang.String
     */
    String menu(User user);
}
