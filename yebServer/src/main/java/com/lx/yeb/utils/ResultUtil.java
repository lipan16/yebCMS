package com.lx.yeb.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ResultUtil
 * @Description 返回页面的json数据
 * @Author lipan
 * @Date 2021/3/18 10:20
 * @Version 1.0
 */
public class ResultUtil{
    public static String FLAGS = "flags"; // 返回码
    public static String INFO  = "info"; // 返回数据 、错误信息、状态消息

    /**
     * fetch 返回无参的成功响应
     *
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/18 10:46
     */
    public static String success(){
        Map<String, Object> map = new HashMap<>(4);
        map.put(FLAGS, ResultCodeEnum.SUCCESS.getFlags());
        map.put(INFO, ResultCodeEnum.SUCCESS.getInfo());
        ObjectMapper mapper  = new ObjectMapper();
        String       jsonStr = null;
        try{
            jsonStr = mapper.writeValueAsString(map);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * fetch 返回带参数的成功响应
     *
     * @param data
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/18 10:45
     */
    public static String success(Object data){
        Map<String, Object> map = new HashMap<>(4);
        map.put(FLAGS, ResultCodeEnum.SUCCESS.getFlags());
        map.put(INFO, data);
        ObjectMapper mapper  = new ObjectMapper();
        String       jsonStr = null;
        try{
            jsonStr = mapper.writeValueAsString(map);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * fetch 返回无参的标准错误
     *
     * @return java.lang.String
     * @author lipan
     * @date 2021/3/18 10:49
     */
    public static String error(){
        Map<String, Object> map = new HashMap<>(4);
        map.put(FLAGS, ResultCodeEnum.FAILURE.getFlags());
        map.put(INFO, ResultCodeEnum.FAILURE.getInfo());
        ObjectMapper mapper  = new ObjectMapper();
        String       jsonStr = null;
        try{
            jsonStr = mapper.writeValueAsString(map);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * fetch 返回自定义错误
     * @author lipan
     * @date 2021/3/19 14:25
     * @param resultCodeEnum
     * @return java.lang.String
     */
    public static String error(ResultCodeEnum resultCodeEnum){
        Map<String, Object> map = new HashMap<>(4);
        map.put(FLAGS, resultCodeEnum.getFlags());
        map.put(INFO, resultCodeEnum.getInfo());
        ObjectMapper mapper  = new ObjectMapper();
        String       jsonStr = null;
        try{
            jsonStr = mapper.writeValueAsString(map);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonStr;
    }
}
