package com.lx.yeb.filter;

import com.lx.yeb.config.SecurityConfig;
import com.lx.yeb.security.UserDetailsServiceImpl;
import com.lx.yeb.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName JwtAuthenticationFilter  JWT验证请求过滤器
 * @Description 从http头的Authorization 项读取token数据，然后用JwtUtil提供的方法校验token的合法性
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * @Author lipan
 * @Date 2021/4/7 17:06
 * @Version 1.0
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException{
        log.info("spring security JWT验证请求过滤器");
        httpServletResponse.setContentType("application/json;charset=utf-8");

        String url   = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader("Authorization");
        if(Arrays.asList(SecurityConfig.AUTH_WHITELIST).contains(url)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 存在token
        if(StringUtils.hasText(token)){
            String username = JwtUtil.getUsernameByToken(token);
            // token存在用户名但是未登录
            if(StringUtils.hasText(username) && null == SecurityContextHolder.getContext()
                                                                             .getAuthentication()){
                // 登录
                log.info("security has userinfo");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 验证token是否有效
                if(null != userDetails){
                    //将认证信息放到SpringSecurity上下文中，给后续的SpringSecurity鉴权使用，如果不放，SpringSecurity就不能鉴权
                    UsernamePasswordAuthenticationToken uPAT = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails
                            .getAuthorities());
                    uPAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(uPAT);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
