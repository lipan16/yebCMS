package com.lx.yeb.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @ClassName User
 * @Description 用户信息
 * @Author lipan
 * @Date 2021/3/18 10:01
 * @Version 1.0
 */
@Data
public class User implements UserDetails{
    private Integer userid;
    private String  username;
    private String  password;
    private String  icon;
    private String  authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
