package com.lx.yeb.filter;

import com.lx.yeb.security.UserDetailsServiceImpl;
import com.lx.yeb.utils.JwtUtil;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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

        // 获取请求 url 来判断是否是登录
        String requestUri = httpServletRequest.getRequestURI();
        // 请求类型
        String method = httpServletRequest.getMethod();
        if("/api/login".equals(requestUri) && "POST".equalsIgnoreCase(method)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = httpServletRequest.getHeader("Authorization");
        // 存在token
        if(StringUtils.hasText(token)){
            try{
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
            }catch(ExpiredJwtException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_EXPIRED));
                httpServletResponse.getWriter().flush();
                log.error("token已过期", e);
            }catch(UnsupportedJwtException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_UNSUPPORTED));
                httpServletResponse.getWriter().flush();
                log.error("token格式错误", e);
            }catch(MalformedJwtException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_MALFORMED));
                httpServletResponse.getWriter().flush();
                log.error("token没有被正确构造", e);
            }catch(SignatureException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_SIGNATURE));
                httpServletResponse.getWriter().flush();
                log.error("token签名失败", e);
            }catch(IllegalArgumentException e){
                httpServletResponse.getWriter()
                                   .println(ResultUtil.error(ResultCodeEnum.TOKEN_ILLEGAL_ARGUMENT));
                httpServletResponse.getWriter().flush();
                log.error("token非法参数异常", e);
            }catch(Exception e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_ERROR));
                httpServletResponse.getWriter().flush();
                log.error("Invalid Token", e);
            }
        }else{
            // SecurityContextHolder.clearContext();
            // httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.USER_NOT_LOGIN));
            // httpServletResponse.getWriter().flush();
            // log.error(ResultUtil.error(ResultCodeEnum.USER_NOT_LOGIN));
            // return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
