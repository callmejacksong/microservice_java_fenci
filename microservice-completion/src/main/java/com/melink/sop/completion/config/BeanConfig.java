package com.melink.sop.completion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Bean
    public SpringContextUtil getContext() {
        return new SpringContextUtil();
    }
}
