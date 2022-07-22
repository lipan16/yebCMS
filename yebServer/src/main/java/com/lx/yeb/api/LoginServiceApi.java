package com.lx.yeb.api;

import com.lx.yeb.bean.YebUser;
import com.lx.yeb.service.AsyncService;
import com.lx.yeb.service.LoginService;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import com.lx.yeb.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * fetch 用户登录相关操作
 * @author lipan
 * @date 2021/7/5 22:11
 * @param
 * @return
 */
@Slf4j
@Api(tags = "用户登录相关操作")
@RestController
@RequestMapping(path = "/api", produces = "application/json;charset=UTF-8")
public class LoginServiceApi{
    @Resource
    private LoginService loginService;
    @Resource
    private AsyncService asyncService;

    // 测试异步线程
    @GetMapping("/lipan/async")
    public void async(){
        asyncService.executeAsync();
    }


    // @ApiOperation("获取验证码图片")
    // @GetMapping(path = "/verifyImg")
    // public void getVerifyImg(HttpServletRequest request, HttpServletResponse response){
    //     try{
    //         response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
    //         response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
    //         response.setHeader("Cache-Control", "no-cache");
    //
    //         VerificationCode verificationCode = new VerificationCode();
    //         verificationCode.getVerifyCode(request, response);
    //     }catch(Exception e){
    //         log.error("获取验证码失败>>>", e);
    //     }
    // }

    @ApiOperation(value = "登录方法")
    @ApiImplicitParam(name = "userVO", value = "登录的视图对象", required = true, dataType = "UserVO", dataTypeClass = UserVO.class)
    @PostMapping(path = {"/login"})
    public String login(@RequestBody UserVO userVO){
        log.info("[前端接口调用]: /api/login");
        YebUser yebUser = new YebUser();
        BeanUtils.copyProperties(userVO, yebUser, YebUser.class);
        if(!StringUtils.hasText(yebUser.getUsername()) || !StringUtils.hasText(yebUser.getPassword())){
            return ResultUtil.error(ResultCodeEnum.ADMIN_NOT_BE_NULL);
        }
        return loginService.verifyLogin(yebUser);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public String logout(){
        log.info("注销成功");
        return ResultUtil.success("注销成功");
    }

    // @ApiOperation(value = "刷新token")
    // @ApiImplicitParam(name = "userVO", value = "登录的视图对象", required = true, dataType = "UserVO", dataTypeClass = UserVO.class)
    // @PostMapping(path = "/refreshToken")
    // public String refreshToken(@RequestBody UserVO userVO){
    //     log.info("[前端接口调用]: /api/refreshToken");
    //     YebUser yebUser = new YebUser();
    //     BeanUtils.copyProperties(userVO, yebUser, YebUser.class);
    //     if(!StringUtils.hasText(yebUser.getUsername())){
    //         return ResultUtil.success(ResultCodeEnum.TOKEN_REFRESH_FAILED);
    //     }
    //     return loginService.refreshToken(yebUser);
    // }

    @ApiOperation(value = "获取当前用户的菜单栏")
    @PostMapping(path = "/nav")
    public String menu(@RequestBody UserVO userVO){
        log.info("[前端接口调用]: /api/nav");
        YebUser yebUser = new YebUser();
        BeanUtils.copyProperties(userVO, yebUser, YebUser.class);
        return loginService.menu(yebUser);
    }

    @GetMapping(path = "/get")
    public String getTest(){
        return "90";
    }

}
