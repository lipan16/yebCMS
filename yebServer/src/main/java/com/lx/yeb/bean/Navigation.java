package com.lx.yeb.bean;

import lombok.Data;

import java.util.List;

@Data
public class Navigation{
    private Integer          id;
    private String           url;
    private String           name; //名称
    private String           path; //路径
    private String           component; //组件
    private String           icon; //图标
    private Boolean          enabled; //是否在导航栏显示
    private Boolean          keepAlive;
    private Boolean          requireAuth;
    private Integer          parentId;
    private List<Navigation> children; //二级导航栏
}
