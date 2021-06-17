package com.app.dmm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication

public class DmmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmmApplication.class, args);
    }
    //允许所有跨域请求
}
