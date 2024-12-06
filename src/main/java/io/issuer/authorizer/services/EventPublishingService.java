package io.issuer.authorizer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Qualifier("default")
public class EventPublishingService {
    private final static Logger log = LoggerFactory.getLogger(EventPublishingService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public EventPublishingService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, String message) {
        try {
            log.info("Publishing message: {}", message);
            kafkaTemplate.send(topic, message).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
