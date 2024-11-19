package com.mosinsa.reaction.infra.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProduceTemplate {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper om;


	public void produce(String topic, Object payload) {
		log.info("publish {}", topic);
		kafkaTemplate.send(topic, getPayloadFromObject(payload));
	}

	protected String getPayloadFromObject(Object event) {
		try {
			return om.writeValueAsString(event);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
