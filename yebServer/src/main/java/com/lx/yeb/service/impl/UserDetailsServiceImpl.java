package com.lx.yeb.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException{
        log.info("执行之定义登录逻辑");
        if(!"lipan".equals(s)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        String password = passwordEncoder.encode("lipan");

        return new User(s, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal," +
                "ROLE_admin"));
    }
}
