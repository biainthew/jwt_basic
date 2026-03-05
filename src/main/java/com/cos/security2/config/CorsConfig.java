package com.cos.security2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 쿠키나 인증 헤더를 포함한 요청을 허용할지 여부
        config.addAllowedOrigin("*"); // 모든 ip 에 응답을 허용
        config.addAllowedHeader("*"); // 모든 header 에 응답을 허용
        config.addAllowedMethod("*"); // 모든 post, get 등 허용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
