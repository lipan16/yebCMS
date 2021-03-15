package com.lx.yeb.service.impl;

import com.lx.yeb.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService{

    @Async
    @Override
    public Future<String> sendSMS(){
        String result = "lipan";
        return null;
    }

}
