package com.lx.yeb.service.impl;

import com.lx.yeb.bean.Navigation;
import com.lx.yeb.bean.YebUser;
import com.lx.yeb.dao.NavigationDao;
import com.lx.yeb.dao.YebUserDao;
import com.lx.yeb.dto.UserInfoDTO;
import com.lx.yeb.service.LoginService;
import com.lx.yeb.security.UserDetailsServiceImpl;
import com.lx.yeb.utils.JwtUtil;
import com.lx.yeb.utils.ResultCodeEnum;
import com.lx.yeb.utils.ResultUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private YebUserDao    yebUserDao;
    @Resource
    private NavigationDao navigationDao;
    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    private PasswordEncoder        passwordEncoder;

    @Override
    public YebUser findUserByUsername(String username){
        return yebUserDao.findUserByUsername(username);
    }

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
        UserDetails yebUser = userDetailsService.loadUserByUsername(u.getUsername());
        if(passwordEncoder.matches(u.getPassword(), yebUser.getPassword())){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(yebUser, null, yebUser
                    .getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            UserInfoDTO result = new UserInfoDTO();
            String      token  = JwtUtil.createToken(yebUser.getUsername());
            result.setUsername(yebUser.getUsername());
            result.setToken(token);
            return ResultUtil.result(ResultCodeEnum.SUCCESS, result);
        }else{
            return ResultUtil.error(ResultCodeEnum.PASSWORD_ERROR);
        }
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
        String      token  = JwtUtil.createToken(yebUser.getUsername());
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
     *
     * @param parentId
     * @return java.util.List<com.lx.yeb.bean.Navigation>
     * @author lipan
     * @date 2021/3/30 18:47
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
