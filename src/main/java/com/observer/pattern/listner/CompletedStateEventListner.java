package com.observer.pattern.listner;


import com.observer.pattern.model.Locker;
import com.observer.pattern.model.RedisObject;
import com.observer.pattern.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

@Component
@Slf4j
public class CompletedStateEventListner implements Observer {

    @Autowired
    private RedisService redisService;

    @Override
    public void update(Observable o, Object message) {
        log.info("Message received from Observer for Completed Order : {} " , message);
        try {
            Locker request = (Locker) message;
            redisService.putData(request.getLockerId(),new RedisObject());
        }catch (Exception e) {
            log.error("Kafka Input Parsing Exception: {}", e);
            return;
        }
    }
}
