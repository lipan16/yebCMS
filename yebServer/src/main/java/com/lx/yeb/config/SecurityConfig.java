package com.lx.yeb.config;

import com.lx.yeb.bean.User;
import com.lx.yeb.filter.TokenFilter;
import com.lx.yeb.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Resource
    private UserService userService;
    @Resource
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Resource
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userService.getUserByName(username);
            if(null != user){
                return user;
            }
            return null;
        };
    }

    //告诉security走重写的UserDetailsService并且使用重写的passwordEncoder做密码匹配
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    //security的完整配置
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //使用jwt不需要csrf
        http.csrf()
            .disable()
            //基于token不需要session
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            //验证所有请求
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                    "/swagger-ui/**","/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/swagger" +
                            "-resources/**", "/v2/api-docs/**")
            .permitAll()
            //允许登录访问
            .antMatchers("/api/login", "/api/logout")
            .permitAll()
            //除了上面的所有请求都要验证
            .anyRequest()
            .authenticated()
            .and()
            .headers()
            .cacheControl();
        // 添加jwt登录授权过滤器
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权，未登录结果返回
        http.exceptionHandling()
            .accessDeniedHandler(restfulAccessDeniedHandler)
            .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    // jwt登录授权过滤器
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
