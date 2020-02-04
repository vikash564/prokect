package com.observer.pattern.listner;


import com.observer.pattern.entity.LockerEntity;
import com.observer.pattern.model.Locker;
import com.observer.pattern.model.RedisObject;
import com.observer.pattern.repository.LockerRepository;
import com.observer.pattern.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Component
@Slf4j
public class ScheduleJob {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private RedisService redisService;

    @Scheduled(fixedRate = 120000000)
    public void getStatusDetails() {
        try {
            log.info("Inside redis poller to get locker status data from db");
            List<LockerEntity> lockerEntities = lockerRepository.findAll();
            List<Locker> lockerList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(lockerEntities)) {
                for (LockerEntity lockerEntity : lockerEntities) {
                    Locker locker = new Locker();
                    BeanUtils.copyProperties(lockerEntity, locker);
                    lockerList.add(locker);
                }
            }
                log.info("checking locker data {} ", lockerList);
                if (!lockerList.isEmpty()) {
                    for (int i = 0; i < lockerList.size(); i++) {
                        Locker locker = lockerList.get(i);
                        long lockerId = locker.getLockerId();
                        RedisObject redisObject = new RedisObject();
                        redisObject.setDoorId(locker.getDoorId());
                        redisObject.setStatus(locker.getStatus());
                        redisService.putData(lockerId, redisObject);
                    }
                }
        } catch (Exception ex) {
            log.error("Error getting while executing redis poller ", ex.getMessage());
            ex.printStackTrace();
        }

    }

}
