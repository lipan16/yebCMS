package com.lx.yeb.dao;

import com.lx.yeb.bean.YebUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YebUserDao{
    YebUser findByUsername(String username);

    // username是否存在表中
    Integer existUser(YebUser yebUser);

    // 验证数据库中是否有该用户，以及用户密码是否正确
    YebUser verifyLogin(YebUser yebUser);
}
