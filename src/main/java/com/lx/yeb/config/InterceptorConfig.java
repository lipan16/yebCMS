package com.lx.yeb.config;

import com.lx.yeb.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName InterceptorConfig
 * @Description 拦截器配置
 * @Author lipan
 * @Date 2021/3/17 17:27
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{

    @Override
    protected void addInterceptors(InterceptorRegistry registry){
        // 注册一个拦截器 匹配/api
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/api/*/**");
        super.addInterceptors(registry);
    }
}
