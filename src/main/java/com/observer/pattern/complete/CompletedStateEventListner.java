package com.observer.pattern.complete;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

@Component
@Slf4j
public class CompletedStateEventListner implements Observer {

    @Override
    public void update(Observable o, Object message) {
        log.info("Message received from Observer for Completed Order : {} " , message);
    }
}
