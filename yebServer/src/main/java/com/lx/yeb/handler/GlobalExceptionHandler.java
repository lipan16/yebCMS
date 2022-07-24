package com.lx.yeb.handler;

import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
 * 缺点只能处理controller中的异常，不能处理工具类或其他中的异常
 *
 * @author lipan
 * @date 2021/4/26 21:02
 */
@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler{

    @ResponseBody
    @ExceptionHandler(value = ExpiredJwtException.class)
    public String expiredJwtExceptionHandler(HttpServletRequest request, Exception e){
        log.error("token已过期：", e);
        return ResultUtil.error(ResultCodeEnum.TOKEN_EXPIRED);
    }

    @ResponseBody
    @ExceptionHandler(value = UnsupportedJwtException.class)
    public  String unsupportedJwtExceptionHandler(HttpServletRequest request, Exception e){
        log.error("token格式错误：", e);
        return ResultUtil.error(ResultCodeEnum.TOKEN_UNSUPPORTED);
    }

    @ResponseBody
    @ExceptionHandler(value = MalformedJwtException.class)
    public String malformedJwtExceptionHandler(HttpServletRequest request, Exception e){
        log.error("token没有被正确构造：", e);
        return ResultUtil.error(ResultCodeEnum.TOKEN_MALFORMED);
    }

    @ResponseBody
    @ExceptionHandler(value = SignatureException.class)
    public String signatureExceptionHandler(HttpServletRequest request, Exception e){
        log.error("token签名失败：", e);
        return ResultUtil.error(ResultCodeEnum.TOKEN_SIGNATURE);
    }

    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler(HttpServletRequest request, Exception e){
        log.error("非法参数异常：", e);
        return ResultUtil.error(ResultCodeEnum.TOKEN_ILLEGAL_ARGUMENT);
    }

    @ResponseBody
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public String usernameNotFoundExceptionHandler(HttpServletRequest request, Exception e){
        log.error("异常信息：", e);
        return ResultUtil.error(ResultCodeEnum.USERNAME_NOT_EXISTS);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(HttpServletRequest request, Exception e){
        log.error("异常信息：", e);
        return ResultUtil.error(ResultCodeEnum.SERVER_ERROR);
    }
}
