package com.melink.sop.completion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// mapper 接口类扫描包配置
@MapperScan("com.melink.sop.completion.dao")
@EnableDiscoveryClient
public class CompletionApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CompletionApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(CompletionApplication.class, args);
	}
}