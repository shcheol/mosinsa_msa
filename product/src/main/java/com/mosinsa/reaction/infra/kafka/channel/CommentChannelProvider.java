package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.review.infra.jpa.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentChannelProvider implements ChannelProvider{

	private final CommentRepository repository;

	@Override
	public String provide(String targetId) {
		return repository.findProductId(targetId)
				.orElseThrow();
	}
}
