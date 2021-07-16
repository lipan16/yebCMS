package com.lx.yeb.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName VisibleThreadPoolTaskExecutor
 * @Description 查看线程池运行状况
 * @Author lipan
 * @Date 2021/7/16 21:17
 * @Version 1.0
 */
@Slf4j
public class VisibleThreadPoolTaskExecutor extends ThreadPoolTaskExecutor{

    private void showThreadPoolInfo(String prefix){
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        log.info("{}, {}, taskCount: {}, completedTaskCount: {}, activeCount: {}, queueSize: {}",
                this.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size()
        );
    }

    @Override
    public void execute(Runnable task){
        showThreadPoolInfo("1. do execute");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout){
        showThreadPoolInfo("2. do execute");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task){
        showThreadPoolInfo("1. do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task){
        showThreadPoolInfo("2. do submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task){
        showThreadPoolInfo("1. do submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task){
        showThreadPoolInfo("2. do submitListenable");
        return super.submitListenable(task);
    }
}
