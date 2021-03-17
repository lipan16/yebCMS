package com.lx.yeb.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName myInterceptor
 * @Description 拦截器
 * @Author lipan
 * @Date 2021/3/17 17:24
 * @Version 1.0
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor{

    //方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        log.info("自定义拦截器MyInterceptor--->方法执行前");
        return true;
    }

    //方法执行后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        log.info("自定义拦截器MyInterceptor--->方法执行后");

    }

    //页面渲染前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        log.info("自定义拦截器MyInterceptor--->页面渲染前");

    }
}
