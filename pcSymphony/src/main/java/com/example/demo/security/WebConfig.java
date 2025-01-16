package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 경로에 위치한 /uploads/** 경로를 매핑하도록 설정
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/C:/uploads/");  // 실제 외부 경로
    }
}
