package com.lx.yeb.config;

import com.lx.yeb.interceptor.TokenInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName InterceptorConfig
 * @Description 拦截器配置
 * @Author lipan
 * @Date 2021/3/17 17:27
 * @Version 1.0
 */
// @Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry){
        // 使swagger ui的访问重定向到resources/webjars/springfox-swagger-ui/
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry){
        // 注册一个拦截器 匹配/api/**
        // 排除swagger ui 、/api/login和/api/refreshToken
        registry.addInterceptor(new TokenInterceptor())
                .excludePathPatterns("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html")
                .excludePathPatterns("/api/menu", "/api/verifyImg", "/api/login", "/api/refreshToken")
                .addPathPatterns("/api/**");
        super.addInterceptors(registry);
    }
}
