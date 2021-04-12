package com.lx.yeb.service.impl;

import com.lx.yeb.bean.YebUser;
import com.lx.yeb.dao.UserDao;
import com.lx.yeb.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserDao userDao;

    @Override
    public YebUser loadUserByUsername(String s) throws UsernameNotFoundException{
        return new YebUser();
    }
}
