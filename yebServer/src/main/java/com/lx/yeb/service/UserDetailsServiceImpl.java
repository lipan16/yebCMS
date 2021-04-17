package com.lx.yeb.service;

import com.lx.yeb.bean.YebUser;
import com.lx.yeb.dao.YebUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Resource
    YebUserDao      yebUserDao;


    /**
     * 由于security框架我们无法直接获取浏览器输入的用户名密码
     * 故必须重写这个方法，才知道用户名密码
     *
     * @param username 输入的用户名
     * @return UserDetails(接口)
     * @author lipan
     * @date 2021/4/17 17:46
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info("执行自定义登录逻辑");
        if(!"lipan".equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        String password = passwordEncoder.encode("lipan");

        YebUser yebUser = yebUserDao.findByUsername(username);

        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal," + "ROLE_admin"));
    }
}
