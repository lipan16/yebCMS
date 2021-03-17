package com.lx.yeb.dao;

import com.lx.yeb.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao{
    // 查询user表中的所有数据
    List<User> selectAllUser();

    // 验证数据库中是否有该用户，以及用户密码是否正确
    User verifyLogin(User user);
}
