package com.lx.yeb.handler;

import com.lx.yeb.bean.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class GlobalResponseHandler implements ResponseBodyAdvice<Object>{
    Logger logger =LoggerFactory.getLogger(GlobalResponseHandler.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass){
        return true;
    }

    // 拦截response输出处理
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse){
        logger.info("开始拦截response返回请求");
        return new Response(1, "成功", o);
        // logger.info("拦截response请求结束");
    }
}
