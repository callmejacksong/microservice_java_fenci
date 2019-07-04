package com.melink.open.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ Author     ：liyang.
 * @ Date       ：Created in 18:34 2019/3/5
 * @ Description：
 * @ Modified By：
 */
@Component
@ConfigurationProperties(prefix = "syh.result.redis.config")
public class ResultRedisConfig {
    private String host;
    private Integer port;
    private Integer timeout;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
