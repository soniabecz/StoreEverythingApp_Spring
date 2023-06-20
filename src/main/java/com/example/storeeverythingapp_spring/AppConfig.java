package com.example.storeeverythingapp_spring;

import com.example.storeeverythingapp_spring.listeners.CustomSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class AppConfig {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public CustomSessionListener customSessionListener() {
        return new CustomSessionListener();
    }
}