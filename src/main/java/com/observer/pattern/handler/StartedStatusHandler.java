package com.observer.pattern.handler;


import com.observer.pattern.model.Locker;
import com.observer.pattern.service.DataPushHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Observable;

@Slf4j
@Component
@Service
public class StartedStatusHandler extends Observable implements DataPushHandler<Locker> {
    @Override
    public void handle(Locker locker) {
        log.info("checking for STARTED status" , locker);
        log.info(this.countObservers()+" are the number of obsevers for new event");
        setChanged();
        notifyObservers(locker);
    }
}
