package com.mosinsa.order.infra.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProduceTemplate {

	private final KafkaTemplate<String, String> kafkaTemplate;

	private final ObjectMapper om;

	public void produce(String topic, Object payload) {

		String key = UUID.randomUUID().toString();
		log.info("publish {}, key {}", topic, key);
		kafkaTemplate.send(topic, key, getPayloadFromObject(payload));
	}

	protected String getPayloadFromObject(Object event) {
		try {
			return om.writeValueAsString(event);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
