package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.infra.kafka.events.CommentDislikesEvent;
import com.mosinsa.reaction.infra.kafka.events.CommentLikesEvent;
import org.springframework.stereotype.Component;

@Component
public class CommentPayloadGenerator implements PayloadGenerator{

	@Override
	public Object generate(String channel, String targetId, ReactionType reactionType, boolean canceled) {

		if (reactionType.equals(ReactionType.LIKES)) {
			return new CommentLikesEvent(channel, targetId, canceled);
		}
		if (reactionType.equals(ReactionType.DISLIKES)){
			return new CommentDislikesEvent(channel, targetId, canceled);
		}
		throw new RuntimeException();
	}
}
