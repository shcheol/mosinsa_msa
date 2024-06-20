package com.mosinsa.review.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.review.infra.redis.BroadcastMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewLikesHandler {

	private final ReviewRepository reviewRepository;
	private final BroadcastMessageHandler handler;

	@KafkaListener(topics = "review-likes-topic")
	public void broadCastMessage(String message) throws JsonProcessingException {
		ReviewLikesEvent reviewLikesEvent = new ObjectMapper().readValue(message, ReviewLikesEvent.class);

		String productId = findChanel(reviewLikesEvent.reviewId());

		handler.publish(productId, message);

	}

	private String findChanel(String reviewId) {
		return reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(()-> new ReviewException(ReviewError.NOT_FOUNT_REVIEW)).getProductId();
	}

}
