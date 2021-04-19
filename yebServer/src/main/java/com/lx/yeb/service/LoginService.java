package com.lx.yeb.service;

import com.lx.yeb.bean.YebUser;

/**
 * @ClassName LoginService
 * @Description 登录方法接口
 * @Author lipan
 * @Date 2021/3/17 11:44
 * @Version 1.0
 */
public interface LoginService{

    /**
     * fetch 通过用户名来查询用户
     *
     * @param username
     * @return com.lx.yeb.bean.YebUser
     * @author lipan
     * @date 2021/4/19 19:26
     */
    YebUser findUserByUsername(String username);

    /**
     * fetch 验证登录
     *
     * @param yebUser
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/17 13:31
     */
    String verifyLogin(YebUser yebUser);

    /**
     * fetch 刷新token
     *
     * @param yebUser
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/24 13:52
     */
    String refreshToken(YebUser yebUser);

    /**
     * fetch 返回当前用户的导航栏
     *
     * @param yebUser
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/29 16:23
     */
    String menu(YebUser yebUser);
}
