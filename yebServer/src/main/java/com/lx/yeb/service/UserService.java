package com.lx.yeb.service;

import com.lx.yeb.bean.YebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    @Override
    YebUser loadUserByUsername(String s);
}
