package com.observer.pattern.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.observer.pattern.model.Locker;
import com.observer.pattern.service.DataPushHandler;
import com.observer.pattern.service.impl.DataPushHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DataPushHandlerFactory dataPushHandlerFactory;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    private void consumeMessages(String message) {
        log.info("Consumed Message: {}", message);
        try {
//            Locker request = objectMapper.readValue(message, Locker.class);
//            processOrderRequest(request);
        } catch (Exception e) {
            log.error("Kafka Input Parsing Exception: {}", e);
            return;
        }

    }

    private void processOrderRequest(Locker request) {

        if (request != null) {
            log.info("Input Order state :" + request);
            DataPushHandler dataPushHandler = dataPushHandlerFactory.getDataPushHandler(request);
            if (dataPushHandler != null) {
                dataPushHandler.handle(request);
            } else {
                log.info("Error while getting dataPushHandler in KafkaConsumer");
                return;
            }

        }

    }
}
