package com.lx.yeb.service.impl;

import com.lx.yeb.bean.User;
import com.lx.yeb.dao.UserDao;
import com.lx.yeb.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserDao userDao;

    @Override
    public List<User> selectUser(){
        return userDao.selectAllUser();
    }
}
