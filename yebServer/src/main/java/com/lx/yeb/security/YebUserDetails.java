package com.lx.yeb.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @ClassName YebUserDetails
 * @Description 重写spring security 的 UserDetails
 * @Author lipan
 * @Date 2021/5/9 21:21
 * @Version 1.0
 */

public class YebUserDetails implements UserDetails{
    private static final long                                   serialVersionUID = 2132320336427812494L;
    private              String                                 username;
    private              String                                 password;
    private              String                                 icon;
    private              String                                 role;
    // 账户是不是可用 true: 可用
    private              boolean                                enabled;
    private              Collection<? extends GrantedAuthority> authorities;
    // 账号是否未过期 true: 未过期
    private              boolean                                accountNonExpired;
    // 账户是否未锁定
    private              boolean                                accountNonLocked;
    // 密码是否未过期
    private              boolean                                credentialsNonExpired;


    public YebUserDetails(String username, String password, String icon, String role, boolean enabled, Collection<? extends GrantedAuthority> authorities){
        this(username, password, icon, role, enabled, true, true, true, authorities);
    }

    public YebUserDetails(String username, String password, String icon, String role, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities){
        Assert.isTrue(username != null && !"".equals(username) && password != null, "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.role = role;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    public String getIcon(){
        return icon;
    }

    public String getRole(){
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.authorities;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public String getUsername(){
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired(){
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked(){
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }

}
