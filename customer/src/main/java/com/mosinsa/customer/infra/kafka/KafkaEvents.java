package com.mosinsa.customer.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class KafkaEvents {

    private static KafkaTemplate<String, String> kafkaTemplate;

    static void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        KafkaEvents.kafkaTemplate = kafkaTemplate;
    }

    public static void raise(Object event) {
        if (kafkaTemplate == null) {
            log.info("kafkaTemplate is null");
            return;
        }
        String topic = findTopic(event);
        log.info("publish event topic {}", topic);
        try {
            kafkaTemplate.send(topic, new ObjectMapper().writeValueAsString(event));
        } catch (JsonProcessingException e){
            throw new IllegalStateException(e);
        }
    }

	private static String findTopic(Object event) {
		if (event instanceof CustomerCreatedEvent){
			return "mosinsa-customer-created";
		}
		throw new IllegalArgumentException("not found topic");
	}

	private KafkaEvents() {
    }
}
