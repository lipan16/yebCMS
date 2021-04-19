package com.lx.yeb.config;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName Swagger3Config
 * @Description Swagger3配置对象
 * @Author lipan
 * @Date 2021/3/23 17:55
 * @Version 1.0
 */
// @Configuration
// @EnableOpenApi
public class Swagger3Config{

    @Value(value = "${swagger.enable}")
    Boolean swaggerEnable;

    // 前台api
    @Bean
    public Docket frontApi(){
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                                                   .enable(swaggerEnable)
                                                   .groupName("前端接口管理")
                                                   .apiInfo(frontApiInfo())
                                                   // 控制哪些接口暴露给swagger ui面板
                                                   .select()
                                                   // 为当前包下controller生成API文档
                                                   .apis(RequestHandlerSelectors.basePackage("com.lx.yeb.controller"))
                                                   // 指定路径处理 PathSelectors.any()代表不过滤任何路径
                                                   .paths(PathSelectors.any())
                                                   .build()
                                                   // 授权信息设置，必要的header token等认证信息
                                                   .securitySchemes(securitySchemes())
                                                   // 授权信息全局应用
                                                   .securityContexts(securityContexts());
    }

    private ApiInfo frontApiInfo(){
        return new ApiInfoBuilder().title("云E办")
                                   .description("云E办前端测试接口文档")
                                   .contact(new Contact("lipan", null, "lipan16@lzu.edu.cn"))
                                   .version("1.0.0")
                                   .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes(){
        ApiKey apiKey = new ApiKey("Authorization", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts(){
        return Collections.singletonList(SecurityContext.builder()
                                                        .securityReferences(Collections.singletonList(new SecurityReference("Authorization", new AuthorizationScope[]{
                                                                new AuthorizationScope("global", "")
                                                        })))
                                                        .build());
    }

    // @Bean
    public Docket backApi(){
        return new Docket(DocumentationType.OAS_30).enable(swaggerEnable)
                                                   .groupName("后台管理")
                                                   .apiInfo(backApiInfo())
                                                   // 控制哪些接口暴露给swagger ui面板
                                                   .select()
                                                   // 为当前包下controller生成API文档
                                                   .apis(RequestHandlerSelectors.basePackage("com.lx.yeb.controller"))
                                                   // 指定路径处理 PathSelectors.any()代表不过滤任何路径
                                                   .paths(PathSelectors.any())
                                                   .build();
    }

    private ApiInfo backApiInfo(){
        return new ApiInfoBuilder().title("云E办后台")
                                   .description("云E办后台测试接口文档")
                                   .contact(new Contact("lipan", null, "lipan16@lzu.edu.cn"))
                                   .version("1.0")
                                   .build();
    }

}
