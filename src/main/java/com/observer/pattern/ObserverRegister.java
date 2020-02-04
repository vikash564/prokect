package com.observer.pattern;


import com.observer.pattern.listner.AcceptedStateEventListner;
import com.observer.pattern.listner.CancelledStateEventListner;
import com.observer.pattern.listner.CompletedStateEventListner;
import com.observer.pattern.handler.CancelledStatusHandler;
import com.observer.pattern.handler.CompletedStatusHandler;
import com.observer.pattern.handler.NewStatusHandler;
import com.observer.pattern.handler.StartedStatusHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ObserverRegister {


    @Autowired
    private AcceptedStateEventListner acceptedStateEventListner;

    @Autowired
    private CancelledStateEventListner cancelledStateEventListner;

    @Autowired
    private CompletedStateEventListner completedStateEventListner;

    @Autowired
    private NewStatusHandler newStatusHandler;

    @Autowired
    private StartedStatusHandler startedStatusHandler;

    @Autowired
    private CompletedStatusHandler completedStatusHandler;

    @Autowired
    private CancelledStatusHandler cancelledStatusHandler;

    @PostConstruct
    public void init(){
        doRegister();
        log.info("Registering observers");
    }

    private void doRegister(){
        newStatusHandler.addObserver(acceptedStateEventListner);
        startedStatusHandler.addObserver(acceptedStateEventListner);

        completedStatusHandler.addObserver(completedStateEventListner);
        completedStatusHandler.addObserver(cancelledStateEventListner);

        cancelledStatusHandler.addObserver(cancelledStateEventListner);
        cancelledStatusHandler.addObserver(completedStateEventListner);

    }
}
