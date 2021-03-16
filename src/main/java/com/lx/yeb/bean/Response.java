package com.lx.yeb.bean;

public class Response{
    private Integer flag; //返回码
    private String  info; //返回状态消息
    private Object  data; //返回数据

    public Response(Integer flag, String info, Object data){
        this.flag = flag;
        this.info = info;
        this.data = data;
    }

    public String getInfo(){
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public Integer getFlag(){
        return flag;
    }

    public void setFlag(Integer flag){
        this.flag = flag;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    @Override
    public String toString(){
        return "Response{" + "flag=" + flag + ", info='" + info + '\'' + ", data=" + data + '}';
    }
}
