package com.observer.pattern.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedissonClient {
    private static RedissonClient _redissonInstance;
    public static String REDIS_HOST = "my.redishost.com:6379";


    private static org.redisson.api.RedissonClient setupRedis() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDIS_HOST)
                .setConnectionPoolSize(200);

        return Redisson.create(config);
    }

    public static RedissonClient getRedis() {
        if (_redissonInstance == null) {
            _redissonInstance = (RedissonClient) setupRedis();
        }
        return _redissonInstance;
    }

    public static void setRedisHost(String redisHost) {
        log.warn("Setting REDIS_HOST to: " + redisHost);
        REDIS_HOST = redisHost;
    }
}
