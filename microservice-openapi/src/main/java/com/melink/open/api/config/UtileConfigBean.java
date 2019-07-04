package com.melink.open.api.config;

import com.melink.microservice.cache.RedisCache;
import com.melink.open.api.model.util.QiniuConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;


@Configuration
public class UtileConfigBean {

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private ResultRedisConfig resultRedisConfig;

    @Bean(name = "resultCache", destroyMethod = "destroy")
    public RedisCache injectRedisCache() {
        return new RedisCache(injectJedisPool2(), "resultCache");
    }

    @Bean(name = "platformCache", destroyMethod = "destroy")
    public RedisCache injectPlatformCache() {
        return new RedisCache(injectJedisPool(), "platformCache");
    }

    @Bean(name = "jedisPool2", destroyMethod = "destroy")
    public JedisPool injectJedisPool2() {
        return new JedisPool(injectGenericObjectPoolConfig(),
                resultRedisConfig.getHost(),
                resultRedisConfig.getPort(), resultRedisConfig.getTimeout(), resultRedisConfig.getPassword());
    }

    @Bean(name = "jedisPool", destroyMethod = "destroy")
    public JedisPool injectJedisPool() {
        return new JedisPool(injectGenericObjectPoolConfig(),
                redisConfig.getHost(),
                redisConfig.getPort(), redisConfig.getTimeout(), redisConfig.getPassword());
    }

    @Bean
    public GenericObjectPoolConfig injectGenericObjectPoolConfig() {
        GenericObjectPoolConfig gc = new GenericObjectPoolConfig();
        gc.setMaxTotal(300);
        gc.setMaxIdle(50);
        gc.setMinIdle(20);
        gc.setMinEvictableIdleTimeMillis(1000);
        gc.setMaxWaitMillis(300);
        return gc;
    }

    @Bean
    public QiniuConfig injectQiniuConfig() {
        QiniuConfig qiniuConfig = new QiniuConfig();
        qiniuConfig.setAccessKey("uaQgHnNyrG5cK-1HdKzxviAyaITKAUGbrsHQIAK7");
        qiniuConfig.setSecretKey("CxkhgNBmpjJt6BnmeJmQ75tt5fm4eMw-jX1dGnJ3");
        qiniuConfig.setBucket("bqmm");
        qiniuConfig.setPublicAddress("http://7xl6jm.com2.z0.glb.qiniucdn.com/@");
        return qiniuConfig;
    }

}
