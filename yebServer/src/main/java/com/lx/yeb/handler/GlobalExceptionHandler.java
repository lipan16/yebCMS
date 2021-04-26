package com.lx.yeb.handler;

import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 * fetch 全局的异常处理方法 用来返回统一的错误json
 * @author lipan
 * @date 2021/4/26 21:02
 * @param
 * @return
 */
@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler{

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public @ResponseBody
    String usernameNotFoundExceptionHandler(HttpServletRequest request, Exception e) throws Exception{
        log.error("异常信息：", e);
        return ResultUtil.error(ResultCodeEnum.USERNAME_NOT_EXISTS);
    }

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    String exceptionHandler(HttpServletRequest request, Exception e) throws Exception{
        log.error("异常信息：", e);
        return ResultUtil.error(ResultCodeEnum.SERVER_ERROR);
    }
}
