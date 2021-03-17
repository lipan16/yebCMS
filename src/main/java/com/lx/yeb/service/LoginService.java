package com.lx.yeb.service;

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
     * @author lipan
     * @date 2021/3/17 13:31
     * @param username, password
     * @return java.lang.Boolean
     */
    Boolean verifyLogin(String username, String password);
}
