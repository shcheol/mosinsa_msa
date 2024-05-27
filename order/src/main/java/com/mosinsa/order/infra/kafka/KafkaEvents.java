package com.mosinsa.order.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Slf4j
public class KafkaEvents {

	private static KafkaTemplate<String, String> kafkaTemplate;

	static void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
		KafkaEvents.kafkaTemplate = kafkaTemplate;
	}

	public static void raise(String topic, Object event) {
		if (kafkaTemplate == null) {
			log.info("kafkaTemplate is null");
			return;
		}

		String key = UUID.randomUUID().toString();
		log.info("publish {}, key {}", topic, key);
		kafkaTemplate.send(topic, key, getPayloadFromObject(event));
	}

	public static String getPayloadFromObject(Object event){
		try {
			return new ObjectMapper().writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	private KafkaEvents() {
	}
}
