package com.lx.yeb.config;

import com.lx.yeb.filter.TokenFilter;
import com.lx.yeb.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @ClassName SecurityConfig
 * @Description spring security config
 * @Author lipan
 * @Date 2021/4/7 15:15
 * @Version 1.0
 * WebSecurityConfigurerAdapter web安全配置的适配器
 * EnableWebSecurity 会帮助创建一个springSecurityFilterChain过滤器（security框架入门）
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    RestfulAccessDeniedHandler   restfulAccessDeniedHandler;
    @Resource
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    //security的认证配置 告诉security走重写的UserDetailsService并且使用重写的passwordEncoder做密码匹配
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //security的授权配置
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //使用jwt不需要csrf
        http.csrf().disable()
            //基于token不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //验证所有请求
        http.authorizeRequests()
            // 允许对于网站静态资源的无授权访问
            .antMatchers("/swagger-ui/**", "/api/menu").permitAll()
            //允许登录访问
            .antMatchers("/api/login", "/api/logout").permitAll()
            //除了上面的所有请求都要验证
            .anyRequest().authenticated();

        http.headers().cacheControl();
        // 添加jwt登录授权过滤器
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权，未登录结果返回
        http.exceptionHandling()
            .accessDeniedHandler(restfulAccessDeniedHandler)
            .authenticationEntryPoint(restAuthenticationEntryPoint);

        // 记住我
        // http.rememberMe()
        //设置数据源
        // .tokenRepository(persistentRememberMeToken())
        //超时时间
        // .tokenValiditySeconds(60)
        // 自定义登录逻辑
        // .userDetailsService(userDetailsService());
    }

    //告诉security加密方式
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // jwt登录授权过滤器
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }

    // @Bean
    // public JdbcTokenRepositoryImpl persistentRememberMeToken(){
    //     JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
    //     //设置数据源
    //     jdbcTokenRepositoryImpl.setDataSource(dataSource);
    //     //自动建表 第一次启动时开启，第二次关闭
    //     // jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
    //     return jdbcTokenRepositoryImpl;
    // }
}
