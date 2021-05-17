package com.lx.yeb.security;

import com.lx.yeb.bean.YebUser;
import com.lx.yeb.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * fetch 重写 spring security 的 UserDetailsService
 *
 * @param
 * @author lipan
 * @date 2021/5/9 21:36
 * @return
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{
    @Resource
    LoginService loginService;

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
        log.info("账号认证中: {}", username);
        YebUser yebUser = loginService.findUserByUsername(username);
        if(null == yebUser){
            throw new UsernameNotFoundException("用户名不存在");
        }
        log.info("lipan {}", yebUser);

        // 获取用户权限信息
        List<SimpleGrantedAuthority> list  = new ArrayList<>();
        String[]                     roles = yebUser.getRole().split(",");
        for(String each : roles){
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_" + each.trim());
            list.add(sga);
        }

        // 查询用户成功，需匹配用户密码，由security内部实现，只需要把查询的用户名正确密码返回即可
        return new YebUserDetails(yebUser.getUsername(), yebUser.getPassword(), list);
    }
}
