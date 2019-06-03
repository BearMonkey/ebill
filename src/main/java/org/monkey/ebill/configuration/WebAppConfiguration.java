package org.monkey.ebill.configuration;

import org.monkey.ebill.interceptor.TokenInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebAppConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("requert!!!!");
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
    }
}
