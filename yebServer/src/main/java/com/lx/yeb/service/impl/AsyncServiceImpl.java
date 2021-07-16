package com.lx.yeb.service.impl;

import com.lx.yeb.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName AsyncServiceImpl
 * @Description 异步线程实现类
 * @Author lipan
 * @Date 2021/7/16 21:15
 * @Version 1.0
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService{
    /**
     * fetch:   asyncServiceExecutor方法是前面ThreadPoolExecutorConfig.java中的方法名，
     *          表明executeAsync方法进入的线程池是asyncServiceExecutor方法创建的。
     * @author lipan
     * @date 2021/7/16 21:16
     * @param []
     * @return void
     */
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        log.info("start executeAsync");

        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");

        log.info("end executeAsync");
    }
}
