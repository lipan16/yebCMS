package com.lx.yeb.config;

import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RestfulAccessDeniedHandler
 * @Description 当访问接口没有权限时，自定义的返回结果
 * @Author lipan
 * @Date 2021/4/7 19:24
 * @Version 1.0
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(ResultUtil.error(ResultCodeEnum.NO_PERMISSION));
        response.getWriter().flush();
    }
}
