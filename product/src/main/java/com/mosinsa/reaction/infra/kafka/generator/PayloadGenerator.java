package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;

public interface PayloadGenerator {

	Object generate(String channel, String targetId, ReactionType reactionType, boolean canceled);
}
