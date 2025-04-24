package com.example.demo.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// nginx + ingress를 이용해 cors문제를 해결하면 백엔드에서는 cors설정을 해지해주어야한다.
//@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173")
//                .allowedOrigins("http://localhost:8011")    // 프론트는 컨테이너포트 8011을 통해 요청이 올것이다.
                .allowedOrigins("http://localhost:30000")   // 프론트의 워커 노드는 30000번으로 띄워질 것이다.
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
