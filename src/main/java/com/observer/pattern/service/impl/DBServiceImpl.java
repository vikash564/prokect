package com.observer.pattern.service.impl;

import com.observer.pattern.entity.LockerEntity;
import com.observer.pattern.model.Locker;
import com.observer.pattern.model.RedisObject;
import com.observer.pattern.repository.LockerRepository;
import com.observer.pattern.service.DBService;
import com.observer.pattern.service.RedisService;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DBServiceImpl implements DBService {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private RedisService redisService;

    public SchemaRegistryClient schemaRegistryClient;

    @Override
    public Locker insert(Locker locker) {
        LockerEntity lockerEntity = new LockerEntity();
        BeanUtils.copyProperties(locker,lockerEntity);
        lockerRepository.save(lockerEntity);
        return locker;
    }

    @Override
    @Transactional
    public Locker update(Locker locker){
        int count = lockerRepository.updateLocker(locker.getLockerId(),locker.getDoorId(),locker.getStatus());
        if (count == 0) {
            locker.setStatus("NA");
        }
        return locker;
    }

    @Override
    public void delete(Locker locker) {
        LockerEntity lockerEntity = lockerRepository.findByLockerId(locker.getLockerId());
        if (lockerEntity == null) {
            log.error("Locker Not Found");
        }
        BeanUtils.copyProperties(locker,lockerEntity);
        lockerRepository.delete(lockerEntity);
    }

    @Override
    public String getLocker(Long lockerId) {

        try {
            Schema schema = schemaRegistryClient.getById(1);
            log.info("checking schema {} " , schema);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        List<RedisObject> status = redisService.getFromRedis(lockerId);;
        return "test";
    }

    @Override
    public String bulkInsert() {
        for(int i=1;i<100;i++)
        {
            Locker locker = new Locker();
            for(int j=0;j<10;j++) {
                locker.setLockerId(Long.valueOf(i));
                locker.setDoorId((long)j);
                locker.setStatus("A");
                locker.setCreatedAt(System.currentTimeMillis());
                insert(locker);
            }

        }
       return "success";
    }
}
