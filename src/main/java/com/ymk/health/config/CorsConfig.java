package com.ymk.health.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域处理配置类
 * Spring容器加载
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 允许访问的路径
                .addMapping("/**")
                // 配置请求来源
                .allowedOrigins("http://localhost:8888")
                // 配置允许跨域访问的方法
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTION")
                // 允许请求头
                .allowCredentials(true)
                // 最大效应时间
                .maxAge(3600);
    }
}
