package com.lx.yeb;

import com.lx.yeb.bean.YebUser;
import com.lx.yeb.service.LoginService;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Arrays;

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
    private LoginService loginService;

    @Resource
    private StringEncryptor stringEncryptor;

    @Test
    void contextLoads(){
        String accessKey = stringEncryptor.encrypt("root");
        String secretKey = stringEncryptor.encrypt("suokou@hunan");
        System.out.println("加密后账号：" + accessKey);
        System.out.println("加密后密码：" + secretKey);
    }

    // @Test
    public void passwordEncoder(){
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String                pwd        = pwdEncoder.encode("lipan");
        System.out.println(pwd);
    }

    // @Test
    public void findUserByUsername(){
        YebUser yebUser = loginService.findUserByUsername("lipan");
        System.out.println(yebUser);
    }

    @Test
    public void test(){
        int[] a = new int[]{2, 0, 2, 1, 1, 0};
        int   min, temp;
        for(int i = 0; i < a.length; i++){
            min = i;
            for(int j = i + 1; j < a.length; j++){
                if(a[min] > a[j]){
                    min = j;
                }
            }
            temp = a[i];
            a[i] = a[min];
            a[min] = temp;
        }
        System.out.println(Arrays.toString(a));
    }
}
