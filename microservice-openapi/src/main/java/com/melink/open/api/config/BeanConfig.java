package com.melink.open.api.config;

import com.melink.microservice.utils.ResultConverUtil;
import com.melink.open.api.spring.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Bean
    public SpringContextUtil getContext() {
        return new SpringContextUtil();
    }

    @Bean
    public ResultConverUtil getResultConverUtils() {
        return new ResultConverUtil();
    }
}
