package com.observer.pattern.handler;

import com.observer.pattern.model.KafkaPojo;
import com.observer.pattern.service.DataPushHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Observable;

@Slf4j
@Component
@Service
public class CompletedStatusHandler extends Observable implements DataPushHandler<KafkaPojo> {

    @Override
    public void handle(KafkaPojo kafkaPojo) {
        log.info("checking for COMPLETED status" , kafkaPojo);
        log.info(this.countObservers()+" are the number of obsevers for new event");
        setChanged();
        notifyObservers(kafkaPojo);
    }
}
