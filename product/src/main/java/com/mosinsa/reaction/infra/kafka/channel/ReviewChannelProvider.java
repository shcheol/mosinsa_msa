package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewChannelProvider implements ChannelProvider{

	private final ReviewRepository repository;

	@Override
	public String provide(String targetId) {
		return repository.findById(ReviewId.of(targetId))
				.orElseThrow()
				.getProductId();
	}
}
