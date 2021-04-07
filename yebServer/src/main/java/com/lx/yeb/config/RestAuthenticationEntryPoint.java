package com.lx.yeb.config;

import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RestAuthenticationEntryPoint
 * @Description 未登录或者token失效访问接口时，自定义的返回结果
 * @Author lipan
 * @Date 2021/4/7 19:27
 * @Version 1.0
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(ResultUtil.error(ResultCodeEnum.USER_NOT_LOGIN));
        response.getWriter().flush();
    }
}
