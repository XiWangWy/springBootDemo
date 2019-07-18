package com.bless.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangxi on 2019/7/12.
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String ip;
    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonClient create(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + ip + ":" + port);
        return  Redisson.create(config);
    }
}
