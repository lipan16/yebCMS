package com.lx.yeb.dao;

import com.lx.yeb.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao{
    // username是否存在表中
    Integer existUser(User user);

    // 验证数据库中是否有该用户，以及用户密码是否正确
    User verifyLogin(User user);
}
