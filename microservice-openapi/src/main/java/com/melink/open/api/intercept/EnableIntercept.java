package com.melink.open.api.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EnableIntercept implements WebMvcConfigurer {

    @Autowired
    RequiredAuthIntercept requiredAuthIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requiredAuthIntercept).addPathPatterns("/**");
    }
}
