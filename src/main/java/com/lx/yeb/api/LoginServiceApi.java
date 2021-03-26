package com.lx.yeb.api;

import com.lx.yeb.bean.Navigation;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public String login(@RequestBody UserVO userVO){
        log.info("[前端接口调用]: /api/login");

        User user = new User();
        BeanUtils.copyProperties(userVO, user, User.class);
        if(!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())){
            return ResultUtil.result(ResultCodeEnum.ADMIN_NOT_BE_NULL);
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
    @GetMapping(path = "/nav")
    public String navigationBar(){
        log.info("[前端接口调用]: /api/nav");
        List<Navigation> nav = new ArrayList<>();

        nav.add(new Navigation("/", "登录", "() => import('../views/login/login.vue')", true, "", null));

        List<Navigation> children = new ArrayList<>();
        Navigation       nav1     = new Navigation("/home/test1", "选项1", "() => import('../views/test/test1.vue')", false, "", null);
        Navigation       nav2     = new Navigation("/home/test1", "选项2", "() => import('../views/test/test1.vue')", false, "", null);
        children.add(nav1);
        children.add(nav2);

        nav.add(new Navigation("/home", "导航1", "() => import('../views/home.vue')", false, "el-icon-location", children));

        nav.add(new Navigation("/home", "导航2", "() => import('../views/home.vue')", false, "el-icon-menu", null));
        return ResultUtil.result(ResultCodeEnum.SUCCESS, nav);
    }
}
