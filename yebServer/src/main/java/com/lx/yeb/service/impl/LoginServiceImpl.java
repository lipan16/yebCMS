package com.lx.yeb.service.impl;

import com.lx.yeb.bean.Navigation;
import com.lx.yeb.bean.YebUser;
import com.lx.yeb.dao.NavigationDao;
import com.lx.yeb.dao.UserDao;
import com.lx.yeb.dto.UserInfoDTO;
import com.lx.yeb.service.LoginService;
import com.lx.yeb.utils.JwtUtil;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private UserDao       userDao;
    @Resource
    private NavigationDao navigationDao;
    // @Resource
    // private PasswordEncoder passwordEncoder;


    /**
     * fetch 验证登录
     *
     * @param u
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/17 13:31
     */
    @Override
    public String verifyLogin(YebUser u){
        int exist = userDao.existUser(u);
        if(exist == 0){
            return ResultUtil.result(ResultCodeEnum.ADMIN_NOT_EXISTS);
        }
        YebUser yebUser = userDao.verifyLogin(u);
        if(yebUser == null){
            return ResultUtil.result(ResultCodeEnum.PASSWORD_ERROR);
        }

        // UsernamePasswordAuthenticationToken authenticationToken =
        //         new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        UserInfoDTO result = new UserInfoDTO();
        String      token  = JwtUtil.createToken(yebUser.getUserid(), yebUser.getUsername());
        result.setUserid(yebUser.getUserid());
        result.setUsername(yebUser.getUsername());
        result.setToken(token);
        return ResultUtil.result(ResultCodeEnum.SUCCESS, result);
    }

    /**
     * fetch 刷新token
     *
     * @param yebUser
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/24 13:52
     */
    @Override
    public String refreshToken(YebUser yebUser){
        UserInfoDTO result = new UserInfoDTO();
        String      token  = JwtUtil.createToken(yebUser.getUserid(), yebUser.getUsername());
        result.setUserid(yebUser.getUserid());
        result.setUsername(yebUser.getUsername());
        result.setToken(token);
        return ResultUtil.result(ResultCodeEnum.SUCCESS, result);
    }

    @Override
    public String menu(YebUser yebUser){
        List<Navigation> navigationList = recursionMenu(0);
        return ResultUtil.result(ResultCodeEnum.SUCCESS, navigationList);
    }

    /**
     * fetch 根据父菜单id选择出所有的子菜单
     * @author lipan
     * @date 2021/3/30 18:47
     * @param parentId
     * @return java.util.List<com.lx.yeb.bean.Navigation>
     */
    private List<Navigation> recursionMenu(Integer parentId){
        List<Navigation> navigationList = navigationDao.selectByParentId(parentId);
        if(navigationList.isEmpty()){
            return null;
        }
        for(Navigation nav : navigationList){
            nav.setChildren(recursionMenu(nav.getId()));
        }
        return navigationList;
    }
}
