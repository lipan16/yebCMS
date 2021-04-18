package com.lx.yeb.filter;

import com.lx.yeb.service.UserDetailsServiceImpl;
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

/**
 * @ClassName TokenFilter
 * @Description JWT授权过滤器 负责在每次请求中，解析请求头中的 JWT，从中取得用户信息，生成验证对象传递给下一个过滤器
 * @Author lipan
 * @Date 2021/4/7 17:06
 * @Version 1.0
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter{
    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException{
        log.info("token过滤器：");
        String authHeader = httpServletRequest.getHeader("Authorization");
        // 存在token
        if(StringUtils.hasText(authHeader)){
            String username = JwtUtil.getTokenInfo(authHeader).toString();
            //token存在用户名但是未登录
            if(StringUtils.hasText(username) && null == SecurityContextHolder.getContext().getAuthentication()){
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 验证token是否有效
                // if()
                UsernamePasswordAuthenticationToken uPAT =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                uPAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(uPAT);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
