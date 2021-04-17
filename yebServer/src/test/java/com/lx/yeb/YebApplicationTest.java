package com.lx.yeb;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName YebApplicationTest
 * @Description yeb test
 * @Author lipan
 * @Date 2021/4/17 18:40
 * @Version 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YebApplicationTest{

    @Test
    public void passwordEncoder(){
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String pwd = pwdEncoder.encode("lipan");
        System.out.println(pwd);
    }
}
