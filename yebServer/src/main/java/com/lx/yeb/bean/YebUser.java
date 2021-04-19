package com.lx.yeb.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName User
 * @Description 用户信息
 * @Author lipan
 * @Date 2021/3/18 10:01
 * @Version 1.0
 */
@Data
public class YebUser implements UserDetails{
    private static final long    serialVersionUID = -4879454135035126119L;
    private              Integer userid;
    private              String  username;
    private              String  password;
    private              String  icon;
    private              String  role;
    private              int     enabled;

    /**
     * fetch
     *
     * @param
     * @return java.util.Collection<? extends GrantedAuthority>
     * <? extends GrantedAuthority> 泛型继承  GrantedAuthority的后代都可以
     * @author lipan
     * @date 2021/4/18 15:06
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        String[]                     roles = role.split(",");
        List<SimpleGrantedAuthority> list  = new ArrayList<>();
        for(String each : roles){
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_" + each.trim());
            list.add(sga);
        }
        return list;
    }

    //账号是否未过期 true: 未过期
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    //账户是否未锁定
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    //密码是否未过期
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    //账户是不是可用 true: 可用
    @Override
    public boolean isEnabled(){
        return this.enabled == 1;
    }
}
