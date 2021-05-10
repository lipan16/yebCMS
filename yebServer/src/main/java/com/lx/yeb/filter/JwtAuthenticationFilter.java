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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

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
public class JwtAuthenticationFilter extends BasicAuthenticationFilter{
    @Resource
    UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException{
        log.info("spring security JWT验证请求过滤器");

        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");

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
                log.error("token已过期", e);
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_EXPIRED));
            }catch(UnsupportedJwtException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_UNSUPPORTED));
                log.error("token格式错误", e);
            }catch(MalformedJwtException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_MALFORMED));
                log.error("token没有被正确构造", e);
            }catch(SignatureException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_SIGNATURE));
                log.error("token签名失败", e);
            }catch(IllegalArgumentException e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_ILLEGAL_ARGUMENT));
                log.error("token非法参数异常", e);
            }catch(Exception e){
                httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.TOKEN_ERROR));
                log.error("Invalid Token", e);
            }finally{
                httpServletResponse.getWriter().flush();
            }
        }else{
            SecurityContextHolder.clearContext();
            httpServletResponse.getWriter().println(ResultUtil.error(ResultCodeEnum.USER_NOT_LOGIN));
            httpServletResponse.getWriter().flush();
            return;
        } filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
