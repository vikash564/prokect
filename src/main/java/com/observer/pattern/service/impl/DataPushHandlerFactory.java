package com.observer.pattern.service.impl;

import com.observer.pattern.handler.CancelledStatusHandler;
import com.observer.pattern.handler.CompletedStatusHandler;
import com.observer.pattern.handler.NewStatusHandler;
import com.observer.pattern.handler.StartedStatusHandler;
import com.observer.pattern.model.Locker;
import com.observer.pattern.service.DataPushHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataPushHandlerFactory {

    @Autowired
    private NewStatusHandler newStatusHandler;

    @Autowired
    private StartedStatusHandler startedStatusHandler;

    @Autowired
    private CompletedStatusHandler completedStatusHandler;

    @Autowired
    private CancelledStatusHandler cancelledStatusHandler;

    public DataPushHandler getDataPushHandler(Locker message) {
        switch (message.getState()){
            case NEW:
                return newStatusHandler;
            case STARTED:
                return startedStatusHandler;
            case CANCELLED:
                return cancelledStatusHandler;
            case COMPLETED:
                return completedStatusHandler;
            default:
                return null;
        }
    }


}
