package com.lx.yeb.config;

import com.lx.yeb.filter.TokenFilter;
import com.lx.yeb.service.UserDetailsServiceImpl;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SecurityConfig
 * @Description spring security config
 * @Author lipan
 * @Date 2021/4/7 15:15
 * @Version 1.0
 * WebSecurityConfigurerAdapter web安全配置的适配器
 * EnableWebSecurity 会帮助创建一个springSecurityFilterChain过滤器（security框架入口）
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Resource
    private UserDetailsServiceImpl userDetailsService;

    // security的认证配置 告诉security走重写的UserDetailsService并且使用重写的passwordEncoder做密码匹配
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        log.info("认证配置");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // security的授权配置
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        log.info("授权配置");
        // 使用jwt不需要csrf
        http.csrf().disable()
            // 允许跨域
            .cors().and()
            // 基于token不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 验证所有请求
        http.authorizeRequests()
            // 允许访问网站静态资源
            // .antMatchers("/favicon.ico", "/swagger-ui/**").permitAll()
            // 测试
            .antMatchers("/api/menu").permitAll()
            .antMatchers("/api/hello").hasRole("admin")
            // 允许登录访问
            .antMatchers("/api/login", "/api/logout").permitAll()
            // 除了上面的所有请求都要验证
            .anyRequest().authenticated();

        // http.formLogin();

        http.headers().cacheControl();
        // 添加jwt登录认证过滤器
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权，未登录结果返回
        http.exceptionHandling()
            .accessDeniedHandler(new RestfulAccessDeniedHandler())
            .authenticationEntryPoint(new RestAuthenticationEntryPoint());

        // 记住我
        // http.rememberMe()
        // 设置数据源
        // .tokenRepository(persistentRememberMeToken())
        // 超时时间
        // .tokenValiditySeconds(60)
        // 自定义登录逻辑
        // .userDetailsService(userDetailsService());
    }

    // 告诉security加密方式
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // jwt登录授权过滤器
    @Bean
    TokenFilter tokenFilter(){
        return new TokenFilter();
    }

    // @Bean
    // public JdbcTokenRepositoryImpl persistentRememberMeToken(){
    //     JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
    //     // 设置数据源
    //     jdbcTokenRepositoryImpl.setDataSource(dataSource);
    //     // 自动建表 第一次启动时开启，第二次关闭
    //     // jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
    //     return jdbcTokenRepositoryImpl;
    // }
}

/**
 * @ClassName RestfulAccessDeniedHandler
 * @Description 当访问接口没有权限时，自定义的返回结果 403
 * @Author lipan
 * @Date 2021/4/7 19:24
 * @Version 1.0
 */
class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(ResultUtil.error(ResultCodeEnum.NO_PERMISSION));
        response.getWriter().flush();
    }
}

/**
 * @ClassName RestAuthenticationEntryPoint
 * @Description 未登录或者token失效访问接口时，自定义的返回结果
 * @Author lipan
 * @Date 2021/4/7 19:27
 * @Version 1.0
 */
class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(ResultUtil.error(ResultCodeEnum.USER_NOT_LOGIN));
        response.getWriter().flush();
    }
}
