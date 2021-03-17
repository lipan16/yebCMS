package com.lx.yeb.service.impl;

import com.lx.yeb.bean.User;
import com.lx.yeb.dao.UserDao;
import com.lx.yeb.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName LoginServiceImpl
 * @Description 登录方法实现
 * @Author lipan
 * @Date 2021/3/17 11:44
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Resource
    private UserDao userDao;

    @Override
    public Boolean verifyLogin(String username, String password){
        User user =  userDao.verifyLogin(new User(username, password));
        System.out.println(user);
        return user != null;
    }
}
