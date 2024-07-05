package com.mosinsa.websocket.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.websocket.redis.BroadcastMessagePublisher;
import com.mosinsa.websocket.redis.WebsocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewLikesHandler {

	private final BroadcastMessagePublisher broadcastMessagePublisher;
	private final ObjectMapper om;

	@KafkaListener(topics = "mosinsa-review-likes")
	public void reviewLikedEvent(String message) throws JsonProcessingException {
		ReviewLikesEvent event = om.readValue(message, ReviewLikesEvent.class);
		log.info("consume message {}", event);

		String productId = event.productId();
		WebsocketMessage websocketMessage = WebsocketMessage.reviewLikes(event.reviewId(), event.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(websocketMessage));
	}

	@KafkaListener(topics = "mosinsa-review-dislikes")
	public void reviewDislikedEvent(String message) throws JsonProcessingException {
		ReviewDislikesEvent event = om.readValue(message, ReviewDislikesEvent.class);
		log.info("consume message {}", event);

		String productId = event.productId();
		WebsocketMessage websocketMessage = WebsocketMessage.reviewDislikes(event.reviewId(), event.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(websocketMessage));
	}
}
