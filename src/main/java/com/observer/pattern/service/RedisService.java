package com.observer.pattern.service;

import com.observer.pattern.model.RedisObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RedisService {
     Boolean putData(Long key , RedisObject value);
     List<RedisObject> getFromRedis(Long key);
     void remove(String key) ;

}
