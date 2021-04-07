package com.lx.yeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig{

    @Bean
    public WebMvcConfigurer myCorsConfig(){
        return new WebMvcConfigurer(){
            @Override
            public void addCorsMappings(CorsRegistry registry){
                //请求/web、/boot路径时允许跨域
                // registry.addMapping("/web/**");
                // registry.addMapping("/boot/**");
            }
        };
    }
}
