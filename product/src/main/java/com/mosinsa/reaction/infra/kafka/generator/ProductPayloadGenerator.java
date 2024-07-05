package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.infra.kafka.events.ProductLikesEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductPayloadGenerator implements PayloadGenerator{
	@Override
	public Object generate(String channel, String targetId, ReactionType reactionType, boolean canceled) {
		return new ProductLikesEvent(channel, canceled);
	}
}
