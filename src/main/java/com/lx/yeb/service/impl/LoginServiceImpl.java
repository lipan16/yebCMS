package com.lx.yeb.service.impl;

import com.lx.yeb.bean.User;
import com.lx.yeb.dao.UserDao;
import com.lx.yeb.dto.UserInfoDTO;
import com.lx.yeb.service.LoginService;
import com.lx.yeb.utils.JwtUtil;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName LoginServiceImpl
 * @Description 登录方法实现
 * @Author lipan
 * @Date 2021/3/17 11:44
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Resource
    private UserDao userDao;

    /**
     * fetch 验证登录
     *
     * @param u
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/17 13:31
     */
    @Override
    public String verifyLogin(User u){
        int exist = userDao.existUser(u);
        if(exist == 0){
            return ResultUtil.result(ResultCodeEnum.ADMIN_NOT_EXISTS);
        }
        User user = userDao.verifyLogin(u);
        if(user == null){
            return ResultUtil.result(ResultCodeEnum.PASSWORD_ERROR);
        }
        UserInfoDTO result = new UserInfoDTO();
        String      token  = JwtUtil.createToken(user.getUserid(), user.getUsername());
        result.setUserid(user.getUserid());
        result.setUsername(user.getUsername());
        result.setToken(token);
        return ResultUtil.result(ResultCodeEnum.SUCCESS, result);
    }

    /**
     * fetch 刷新token
     *
     * @param user
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/24 13:52
     */
    @Override
    public String refreshToken(User user){
        UserInfoDTO result = new UserInfoDTO();
        String token = JwtUtil.createToken(user.getUserid(), user.getUsername());
        result.setUserid(user.getUserid());
        result.setUsername(user.getUsername());
        result.setToken(token);
        return ResultUtil.result(ResultCodeEnum.SUCCESS, result);
    }
}
