package com.mosinsa.websocket.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.review.infra.redis.BroadcastMessagePublisher;
import com.mosinsa.review.infra.redis.ReviewMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewLikesHandler {

	private final ReviewRepository reviewRepository;
	private final BroadcastMessagePublisher broadcastMessagePublisher;
	private final ObjectMapper om;

	@KafkaListener(topics = "review-likes-topic")
	public void reviewLikedEvent(String message) throws JsonProcessingException {
		ReviewLikesEvent reviewLikesEvent = om.readValue(message, ReviewLikesEvent.class);
		log.info("consume message {}", reviewLikesEvent);

		String productId = findChanel(reviewLikesEvent.reviewId());
		ReviewMessage reviewMessage = ReviewMessage.reviewLikes(reviewLikesEvent.reviewId(), reviewLikesEvent.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(reviewMessage));
	}

	@KafkaListener(topics = "review-dislikes-topic")
	public void reviewDislikedEvent(String message) throws JsonProcessingException {
		ReviewDislikesEvent reviewDislikesEvent = om.readValue(message, ReviewDislikesEvent.class);
		log.info("consume message {}", reviewDislikesEvent);

		String productId = findChanel(reviewDislikesEvent.reviewId());
		ReviewMessage reviewMessage = ReviewMessage.reviewDislikes(reviewDislikesEvent.reviewId(), reviewDislikesEvent.canceled());
		broadcastMessagePublisher.publish(productId, om.writeValueAsString(reviewMessage));
	}

	private String findChanel(String reviewId) {
		return reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(()-> new ReviewException(ReviewError.NOT_FOUNT_REVIEW)).getProductId();
	}

}
