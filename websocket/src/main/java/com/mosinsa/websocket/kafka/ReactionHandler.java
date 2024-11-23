package com.mosinsa.websocket.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.websocket.redis.BroadcastMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactionHandler {

	private final BroadcastMessagePublisher broadcastMessagePublisher;
	private final ObjectMapper om;

	@KafkaListener(topics = {
			"mosinsa-reaction-react",
			"mosinsa-reaction-cancel"
	})
	public void reactedEvent(String message) throws JsonProcessingException {
		ReactionEvent event = om.readValue(message, ReactionEvent.class);
		log.info("consume message {}", event);

		String channelId = event.channelId();
		broadcastMessagePublisher.publish(channelId, om.writeValueAsString(event));
	}
}
