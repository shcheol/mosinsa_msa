package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.infra.kafka.events.ReviewDislikesEvent;
import com.mosinsa.reaction.infra.kafka.events.ReviewLikesEvent;
import org.springframework.stereotype.Component;

@Component
public class ReviewPayloadGenerator implements PayloadGenerator {
	@Override
	public Object generate(String channel, String targetId, ReactionType reactionType, boolean canceled) {
		if (reactionType.equals(ReactionType.LIKES)) {
			return new ReviewLikesEvent(channel, targetId, canceled);
		}
		if (reactionType.equals(ReactionType.DISLIKES)) {
			return new ReviewDislikesEvent(channel, targetId, canceled);
		}
		throw new RuntimeException();
	}
}
