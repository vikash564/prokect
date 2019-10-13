package com.observer.pattern.service;

import com.observer.pattern.handler.CancelledStatusHandler;
import com.observer.pattern.handler.CompletedStatusHandler;
import com.observer.pattern.handler.NewStatusHandler;
import com.observer.pattern.handler.StartedStatusHandler;
import com.observer.pattern.model.KafkaPojo;
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

    public DataPushHandler getDataPushHandler(KafkaPojo message) {
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
