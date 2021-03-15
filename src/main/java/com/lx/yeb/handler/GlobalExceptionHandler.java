package com.lx.yeb.handler;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = {Controller.class, RestController.class}) // 用来返回统一的错误json，或者错误页面
public class GlobalExceptionHandler{

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody Object errorHandlerByJson(HttpServletRequest request, Exception e) throws Exception{
        e.printStackTrace();
        return "sorry, 服务器开小差了~";
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView defaultHandlerByView(HttpServletRequest request, Exception e) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e.getMessage());
        mv.addObject("url", request.getRequestURI());

        // 发生异常时跳转到错误页面
        mv.setViewName("50x");
        return mv;
    }

    /**
     * 处理404  页面找不到错误
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return (factory -> {
            ErrorPage error4o1Page = new ErrorPage(HttpStatus.UNAUTHORIZED,"/401.html");
            ErrorPage error4o4Page = new ErrorPage(HttpStatus.NOT_FOUND,"/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/500.html");
            factory.addErrorPages(error4o1Page, error4o4Page, error500Page);
        });
    }
}
