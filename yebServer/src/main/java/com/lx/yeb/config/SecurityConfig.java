package com.lx.yeb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lx.yeb.filter.JwtAuthenticationFilter;
import com.lx.yeb.security.UserDetailsServiceImpl;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    // 允许访问登录接口、网站静态资源、swagger3 /webjars/**
    public static final String[] AUTH_WHITELIST = {"/api/login",
                                                   "/api/logout",
                                                   "/swagger-ui/**",
                                                   "/swagger-resources/**",
                                                   "/v3/api-docs"
    };

    /**
     * fetch security的认证配置 告诉security走重写的UserDetailsService并且使用重写的passwordEncoder做密码匹配
     *
     * @param auth
     * @return void
     * @author lipan
     * @date 2021/5/16 17:46
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        log.info("security认证配置");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        log.info("security授权配置");
        // 使用jwt不需要csrf
        http.csrf().disable()
            // 防止iframe 造成跨域
            .headers().frameOptions().disable().and()
            // 允许跨域
            .cors().and()
            // 基于token不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().cacheControl();

        http.formLogin().successHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
            pw.flush();
            pw.close();
        }).failureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(new ObjectMapper().writeValueAsString(exception.getMessage()));
            pw.flush();
            pw.close();
        }).and().logout().logoutUrl("/api/logout").logoutSuccessHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(new ObjectMapper().writeValueAsString("退出登录"));
            pw.flush();
            pw.close();
        });
        // 验证所有请求
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
            // 测试
            .antMatchers("/api/menu").permitAll().antMatchers("/api/hello").hasRole("admins")
            // 除了上面的所有请求都要验证
            .anyRequest().authenticated();

        // 添加jwt验证请求过滤器
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权，未登录结果返回
        http.exceptionHandling()
            .accessDeniedHandler(new RestfulAccessDeniedHandler())
            .authenticationEntryPoint(new RestAuthenticationEntryPoint());

    }

    /**
     * fetch 告诉security加密方式
     *
     * @param []
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author lipan
     * @date 2021/5/16 17:50
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * fetch JWT验证请求过滤器
     *
     * @param []
     * @return com.lx.yeb.filter.JwtAuthenticationFilter
     * @author lipan
     * @date 2021/5/16 17:50
     */
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
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
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException{
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
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(ResultUtil.error(ResultCodeEnum.USER_NOT_LOGIN));
        response.getWriter().flush();
    }
}
