package com.lx.yeb.controller;

import com.lx.yeb.bean.User;
import com.lx.yeb.service.LoginService;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import com.lx.yeb.utils.VerificationCode;
import com.lx.yeb.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Api(tags = "用户登录相关操作")
@RestController
@RequestMapping(path = "/api", produces = "application/json;charset=UTF-8")
public class LoginServiceApi{

    @Resource
    private LoginService loginService;

    @ApiOperation("获取验证码图片")
    @GetMapping(path = "/verifyImg")
    public void getVerifyImg(HttpServletRequest request, HttpServletResponse response){
        try{
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");

            VerificationCode verificationCode = new VerificationCode();
            verificationCode.getVerifyCode(request, response);
        }catch(Exception e){
            log.error("获取验证码失败>>>", e);
        }
    }

    @ApiOperation(value = "登录方法")
    @ApiImplicitParam(name = "userVO", value = "登录的视图对象", required = true, dataType = "UserVO", dataTypeClass = UserVO.class)
    @PostMapping(path = {"/login"})
    public String login(@RequestBody UserVO userVO, HttpSession session){
        log.info("[前端接口调用]: /api/login");

        // if(!StringUtils.hasText(userVO.getVerifyCode())){
        //     return ResultUtil.error(ResultCodeEnum.VERIFICATION_BE_REQUIRED);
        // }
        // Long codeTime = (Long) session.getAttribute("codeTime");
        // if((System.currentTimeMillis() - codeTime) / 1000 / 60 > 5){
        //     return ResultUtil.error(ResultCodeEnum.VERIFICATION_EXPIRED);
        // }
        // String verifyCode = (String) session.getAttribute("verifyCode");
        // if(!verifyCode.equals(userVO.getVerifyCode())){
        //     return ResultUtil.error(ResultCodeEnum.VERIFICATION_FAILED);
        // }

        User user = new User();
        BeanUtils.copyProperties(userVO, user, User.class);
        if(!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())){
            return ResultUtil.error(ResultCodeEnum.ADMIN_NOT_BE_NULL);
        }
        return loginService.verifyLogin(user);
    }

    @ApiOperation(value = "刷新token")
    @ApiImplicitParam(name = "userVO", value = "登录的视图对象", required = true, dataType = "UserVO", dataTypeClass = UserVO.class)
    @PostMapping(path = "/refreshToken")
    public String refreshToken(@RequestBody UserVO userVO){
        log.info("[前端接口调用]: /api/refreshToken");
        User user = new User();
        BeanUtils.copyProperties(userVO, user, User.class);
        if(!StringUtils.hasText(user.getUserid().toString()) || !StringUtils.hasText(user.getUsername())){
            return ResultUtil.result(ResultCodeEnum.TOKEN_REFRESH_FAILED);
        }
        return loginService.refreshToken(user);
    }

    @ApiOperation(value = "获取当前用户的菜单栏")
    @PostMapping(path = "/menu")
    public String menu(@RequestBody UserVO userVO){
        log.info("[前端接口调用]: /api/menu");
        User user = new User();
        BeanUtils.copyProperties(userVO, user, User.class);
        return loginService.menu(user);
    }
}
