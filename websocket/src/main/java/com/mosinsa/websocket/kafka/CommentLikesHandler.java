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
public class CommentLikesHandler {

	private final BroadcastMessagePublisher broadcastMessagePublisher;
	private final ObjectMapper om;

	@KafkaListener(topics = "comment-likes-topic")
	public void commentLikedEvent(String message) throws JsonProcessingException {
		CommentLikesEvent event = om.readValue(message, CommentLikesEvent.class);
		log.info("consume message {}", event);

		String productId = event.productId();
		WebsocketMessage websocketMessage = WebsocketMessage.commentLikes(event.reviewId(), event.commentId(), event.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(websocketMessage));
	}

	@KafkaListener(topics = "comment-dislikes-topic")
	public void commentDislikedEvent(String message) throws JsonProcessingException {
		CommentDislikesEvent event = om.readValue(message, CommentDislikesEvent.class);
		log.info("consume message {}", event);

		String productId = event.productId();
		WebsocketMessage websocketMessage = WebsocketMessage.commentDislikes(event.reviewId(),event.commentId(), event.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(websocketMessage));
	}
}
