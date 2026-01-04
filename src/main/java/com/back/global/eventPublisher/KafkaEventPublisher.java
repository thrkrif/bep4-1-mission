package com.back.global.eventPublisher;

import com.back.standard.event.HaveEventName;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {
    private final KafkaTemplate<String, HaveEventName> kafkaTemplate;

    public void publish(HaveEventName event){
        kafkaTemplate.send(event.getEventName(), event);
    }
}
