package com.observer.pattern.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.observer.pattern.model.Locker;
import com.observer.pattern.model.RedisObject;
import com.observer.pattern.service.DBService;
import com.observer.pattern.service.RedisService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private DBService dbService;

    public static RedissonClient redissonClient = null;


    @Override
    public Boolean putData(Long key, RedisObject value) {
        if (redissonClient == null) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://localhost:6379");
//                    .setConnectionPoolSize(50);
            redissonClient = Redisson.create(config);
        }
        if (key == null || value == null) {
            return null;
        }
        RLock lock = redissonClient.getLock(key + "test");
        lock.lock(5, TimeUnit.MILLISECONDS);
        try {
            if (lock.tryLock()) {
                    Gson gson = new Gson();
                    Jedis jedis = new Jedis();
                    String json = gson.toJson(value);
                    jedis.lrem(key+"-locker",1,json);
                    redisTemplate.opsForList().rightPush(key+"-locker", json);

            } else {
                return false;
            }
        } catch (Exception ex) {
            log.error("Not able to put data in redis for key {} ", key);
            return false;
        } finally {
            lock.forceUnlock();
        }
        return true;
    }

    @Override
    public List<RedisObject> getFromRedis(Long key) {
        log.info("Received input parameters for containsKey : key : {}", key);
        if (key == null) {
            log.error("key is not valid");
            return null;
        }
        List<String> redisValue=null;
        RedisObject redisObjectTemp;
        List<RedisObject> redisObjectList = new ArrayList<>();
        String res;
        try {
            redisValue = redisTemplate.opsForList().range(key+"-locker",0,-1);
            log.info("value is : {} " , redisValue);
        } catch (Exception e) {
            log.error("Not able to get value from redis for key {} ", key);
        }
        try {
            for(String value : redisValue) {
                RedisObject redisObject = new RedisObject();
                Gson gson = new Gson();
                redisObjectTemp = gson.fromJson(value,RedisObject.class);
                if (redisObjectTemp.getStatus().equalsIgnoreCase("A")) {
                    redisObject.setStatus("NA");
                    redisObject.setDoorId(redisObjectTemp.getDoorId());
                    boolean isUpdateReq = putData(key,redisObject);
                    if (isUpdateReq) {
                        res = updateStatusInDb(key,redisObjectTemp.getDoorId());
                    } else {
                        res = "NA";
                    }
                    redisObject.setDoorId(redisObjectTemp.getDoorId());
                    redisObject.setStatus(res);
                    redisObjectList.add(redisObject);
                   return redisObjectList;
                }
            }
        } catch (Exception ex) {
            log.error("Not able to put data in redis for key {} ", key);
        }
        return redisObjectList;
    }

    @Override
    public void remove(String key) {
        if (key == null) {
            return;
        }
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("error while deleting cache : {}", e.getMessage());

        }

    }

    private String updateStatusInDb(Long key,Long doorId) {
        Locker locker = new Locker();
        locker.setLockerId(key);
        locker.setDoorId(doorId);
        locker.setStatus("A");
        locker.setCreatedAt(System.currentTimeMillis());
        locker = dbService.update(locker);
        return locker.getStatus();
    }

}
