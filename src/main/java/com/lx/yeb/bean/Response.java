package com.lx.yeb.bean;

public class Response{
    private Integer resCode; //返回码
    private String  resMessage; //返回状态消息
    private Object  data; //返回数据

    public Response(Integer resCode, String resMessage, Object data){
        this.resCode = resCode;
        this.resMessage = resMessage;
        this.data = data;
    }

    public String getResMessage(){
        return resMessage;
    }

    public void setResMessage(String resMessage){
        this.resMessage = resMessage;
    }

    public Integer getResCode(){
        return resCode;
    }

    public void setResCode(Integer resCode){
        this.resCode = resCode;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }
}
