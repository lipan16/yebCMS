package com.lx.yeb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class YebUser implements UserDetails{
    private static final long                         serialVersionUID = -4879454135035126119L;
    private              String                       username;
    private              String                       password;
    private              String                       icon;
    private              String                       role;
    private              int                          enabled;
    private              Collection<GrantedAuthority> authorities;

    public YebUser(String username, String password, Collection<GrantedAuthority> authorities){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
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
        System.out.println("查询用户的角色权限是" + list.toString());
        return list;
    }

    // 账号是否未过期 true: 未过期
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    // 密码是否未过期
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    // 账户是不是可用 true: 可用
    @Override
    public boolean isEnabled(){
        return this.enabled == 1;
    }
}
