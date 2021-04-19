package com.lx.yeb.handler;

import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class}) // 用来返回统一的错误json，或者错误页面
public class GlobalExceptionHandler{

    // 全局的异常处理方法
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    String errorHandler(HttpServletRequest request, Exception e) throws Exception{
        log.error("异常信息：", e);
        return ResultUtil.error(ResultCodeEnum.SERVER_ERROR);
    }


    // 处理404  页面找不到错误
    // @Bean
    // public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
    //     return (factory -> {
    //         ErrorPage error4o1Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
    //         ErrorPage error4o4Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
    //         ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
    //         factory.addErrorPages(error4o1Page, error4o4Page, error500Page);
    //     });
    // }
}
