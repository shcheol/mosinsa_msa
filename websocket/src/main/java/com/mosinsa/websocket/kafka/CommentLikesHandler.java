package com.mosinsa.websocket.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.infra.jpa.CommentRepository;
import com.mosinsa.review.infra.redis.BroadcastMessagePublisher;
import com.mosinsa.review.infra.redis.ReviewMessage;
import com.mosinsa.websocket.redis.BroadcastMessagePublisher;
import com.mosinsa.websocket.redis.ReviewMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentLikesHandler {

	private final CommentRepository commentRepository;
	private final BroadcastMessagePublisher broadcastMessagePublisher;
	private final ObjectMapper om;

	@KafkaListener(topics = "comment-likes-topic")
	public void commentLikedEvent(String message) throws JsonProcessingException {
		CommentLikesEvent event = om.readValue(message, CommentLikesEvent.class);
		log.info("consume message {}", event);

		String productId = findChanel(event.commentId());
		ReviewMessage reviewMessage = ReviewMessage.commentLikes(event.reviewId(), event.commentId(), event.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(reviewMessage));
	}

	@KafkaListener(topics = "comment-dislikes-topic")
	public void commentDislikedEvent(String message) throws JsonProcessingException {
		CommentDislikesEvent event = om.readValue(message, CommentDislikesEvent.class);
		log.info("consume message {}", event);

		String productId = findChanel(event.commentId());
		ReviewMessage reviewMessage = ReviewMessage.commentDislikes(event.reviewId(),event.commentId(), event.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(reviewMessage));
	}

	private String findChanel(String commentId) {
		return commentRepository.findProductId(commentId)
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_COMMENT));
	}

}
