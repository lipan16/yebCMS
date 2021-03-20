package com.lx.yeb.interceptor;

import com.lx.yeb.annotation.JwtIgnore;
import com.lx.yeb.utils.JwtUtil;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @ClassName TokenInterceptor
 * @Description token拦截器
 * @Author lipan
 * @Date 2021/3/17 17:24
 * @Version 1.0
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor{

    //方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        log.info("token拦截器TokenInterceptor--->方法执行前");
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore     jwtIgnore     = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if(jwtIgnore != null){
                return true;
            }
        }

        // 获取请求头中的authorization信息
        String authorization = request.getHeader("Authorization");
        if(!StringUtils.hasText(authorization)){
            log.info("用户未登录，请先登录");
            writeContent(ResultUtil.result(ResultCodeEnum.USER_NOT_LOGIN));
            return false;
        }
        ResultCodeEnum resultCodeEnum = JwtUtil.getTokenInfo(authorization);

        // 解析token失败或者token已失效
        if(resultCodeEnum != ResultCodeEnum.SUCCESS){
            writeContent(ResultUtil.error(resultCodeEnum));
            return false;
        }
        return true;
    }

    //方法执行后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        log.info("token拦截器TokenInterceptor--->方法执行后");

    }

    //页面渲染前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        log.info("token拦截器TokenInterceptor--->页面渲染前");
    }

    /**
     * fetch 发送响应报文
     *
     * @param content
     * @return void
     * @author lipan
     * @date 2021/3/19 11:52
     */
    private void writeContent(String content){
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getResponse();
        Objects.requireNonNull(response).reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try(PrintWriter writer = response.getWriter()){
            writer.print(content);
            writer.flush();
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }
}
