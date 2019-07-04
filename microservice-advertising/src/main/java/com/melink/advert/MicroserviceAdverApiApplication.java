package com.melink.advert;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.melink.advert.mapper.netPic")
public class MicroserviceAdverApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAdverApiApplication.class);
    }
}
