package com.lx.yeb.dao;

import com.lx.yeb.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao{
    // 查询user表中的所有数据
    public List<User> selectAllUser();

    // 查询user by ID
    // public User selectUserById(Integer id);

}
