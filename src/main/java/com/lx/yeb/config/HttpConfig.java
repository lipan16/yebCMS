package com.lx.yeb.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfig{

    // 编程方式实现http
    // @Bean
    public TomcatServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
            //安全限制：http访问时重定向到https
            @Override
            protected void postProcessContext(Context context){
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection =new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(createHttpConnector());
        return tomcat;
    }

    private Connector createHttpConnector(){
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);//http访问时重定向到https
        return connector;
    }

    // 编程方式实现https，而不是在properties文件中
    // @Bean
    // public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer(){
    //     return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>(){
    //         @Override
    //         public void customize(ConfigurableWebServerFactory factory){
    //             Ssl ssl = new Ssl();
    //             String path = HttpConfig.class.getClassLoader().getResource("springboot.jks").getPath();
    //             ssl.setKeyStore(path);
    //             ssl.setKeyStorePassword("springboot");
    //             ssl.setKeyStoreType("jks");
    //             factory.setSsl(ssl);
    //             factory.setPort(8443);
    //         }
    //     };
    // }

}
