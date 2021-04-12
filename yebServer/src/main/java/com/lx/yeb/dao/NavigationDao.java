package com.lx.yeb.dao;

import com.lx.yeb.bean.Navigation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName NavigationDao
 * @Description 菜单导航栏接口
 * @Author lipan
 * @Date 2021/3/29 16:47
 * @Version 1.0
 */
@Mapper
public interface NavigationDao{
    List<Navigation> selectByParentId(Integer parentId);
}
