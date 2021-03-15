package com.lx.yeb.controller;

import com.lx.yeb.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class AsyncController{

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/async")
    public Object async() throws ExecutionException, InterruptedException{
        //调用service层的异步任务
        Future<String> future = asyncService.sendSMS();
        // return  future.get();
        return null;
    }
}
