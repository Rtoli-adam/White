package com.evan.wj.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry){
        resourceHandlerRegistry.addResourceHandler("api/file/**").addResourceLocations("file:"+"D:/White/img/");
    }
}
