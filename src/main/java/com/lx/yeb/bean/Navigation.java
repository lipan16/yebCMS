package com.lx.yeb.bean;

import java.util.List;

public class Navigation{
    private String           path; //url
    private String           name; //名称
    private String           component; //组件
    private Boolean          hidden; //是否在导航栏显示
    private String           icon; //图标
    private List<Navigation> children; //二级导航栏

    public Navigation(String path, String name, String component, Boolean hidden, String icon, List<Navigation> children){
        this.path = path;
        this.name = name;
        this.component = component;
        this.hidden = hidden;
        this.icon = icon;
        this.children = children;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getComponent(){
        return component;
    }

    public void setComponent(String component){
        this.component = component;
    }

    public Boolean getHidden(){
        return hidden;
    }

    public void setHidden(Boolean hidden){
        this.hidden = hidden;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){

        this.icon = icon;

    }

    public List<Navigation> getChildren(){
        return children;
    }

    public void setChildren(List<Navigation> children){
        this.children = children;
    }

    @Override
    public String toString(){
        return "Navigation{" + "path='" + path + '\'' + ", name='" + name + '\'' + ", component='" + component + '\'' + ", hidden=" + hidden + ", icon='" + icon + '\'' + ", children=" + children + '}';
    }
}
