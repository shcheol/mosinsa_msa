package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.infra.jpa.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentChannelProvider implements ChannelProvider {

	private final CommentRepository repository;

	@Override
	public String provide(String targetId) {
		return repository.findById(targetId)
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_COMMENT))
				.getReview().getReviewId().getId();
	}
}
