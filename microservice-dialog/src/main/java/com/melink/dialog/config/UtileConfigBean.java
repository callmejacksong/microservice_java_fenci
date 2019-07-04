package com.melink.dialog.config;

import com.melink.microservice.cache.RedisCache;
import com.melink.microservice.utils.ResultConverUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
public class UtileConfigBean {

    @Bean(name = "platformCache",destroyMethod = "destroy")
    public RedisCache injectPlatformCache(){
        return new RedisCache(injectJedisPool(), "platformCache");
    }


    @Bean(name = "jedisPool",destroyMethod = "destroy")
    public JedisPool injectJedisPool(){
        return new JedisPool(injectGenericObjectPoolConfig(),
                "r-bp1c83b23766a284.redis.rds.aliyuncs.com",
                6379,5000,"Melink1212");
    }

    @Bean
    public GenericObjectPoolConfig injectGenericObjectPoolConfig(){
        GenericObjectPoolConfig gc = new GenericObjectPoolConfig();
        gc.setMaxTotal(300);
        gc.setMaxIdle(50);
        gc.setMinIdle(20);
        gc.setMinEvictableIdleTimeMillis(1000);
        gc.setMaxWaitMillis(300);
        return gc;
    }

    @Bean
    public ResultConverUtil getResultConverUtils() {
        return new ResultConverUtil();
    }
}
