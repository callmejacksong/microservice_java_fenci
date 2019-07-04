package com.melink.sop.completion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:redis-config.xml"})
public class ConfigClass {
}
