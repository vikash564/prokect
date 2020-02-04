package com.observer.pattern.listner;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

@Component
@Slf4j
public class AcceptedStateEventListner implements Observer {

    @Override
    public void update(Observable o, Object message) {
        log.info("Message received from Observer for Accepted State : {} " , message);
    }
}
