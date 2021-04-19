package com.lx.yeb;


import com.lx.yeb.bean.YebUser;
import com.lx.yeb.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @ClassName YebApplicationTest
 * @Description yeb test
 * @Author lipan
 * @Date 2021/4/17 18:40
 * @Version 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YebApplicationTest{
    @Resource
    LoginService loginService;


    @Test
    public void passwordEncoder(){
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String pwd = pwdEncoder.encode("lipan");
        System.out.println(pwd);
    }

    @Test
    public void findUserByUsername(){
        YebUser yebUser = loginService.findUserByUsername("lipan");
        System.out.println(yebUser);
    }
}
