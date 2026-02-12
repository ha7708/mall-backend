package org.zerock.mallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.mallapi.controller.formatter.LocalDateFormatter;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter());
    }

    // CORS 설정법은 2가지가 있다.
    // 1. @Controller 가 있는 클래스에 @CrossOrigin 적용 : 모든 컨트롤러에 개별적으로 적용해야 한다.
    // 2. SpringSecurity 를 이용하는 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(300)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
        // 주의점 1
        // Spring Security가 켜져 있으면 CORS는 Security 필터 체인에서 먼저 걸러질 수 있음
        // → 그럼 MVC의 addCorsMappings가 제대로 적용 안 되는 케이스가 많다
        // → 이 경우 Security 설정에서 http.cors() 활성화, CorsConfigurationSource 빈 제공
        // 주의점 2
        // 

    }


}
