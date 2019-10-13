package com.observer.pattern.cancel;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

@Slf4j
@Component
public class CancelledStateEventListner implements Observer {

    @Override
    public void update(Observable o, Object message) {
        log.info("Message received from Observer for Cancelled Order : {} " , message);
    }
}
