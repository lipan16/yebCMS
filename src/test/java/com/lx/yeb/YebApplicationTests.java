package com.lx.yeb;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YebApplicationTests{

    @Test
    void contextLoads(){
    }

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd(){
        //加密
        // System.out.println(stringEncryptor.encrypt("root"));
        // System.out.println(stringEncryptor.encrypt("suokou@hunan"));
        //解密
        // System.out.println(stringEncryptor.decrypt("root"));
    }

}
