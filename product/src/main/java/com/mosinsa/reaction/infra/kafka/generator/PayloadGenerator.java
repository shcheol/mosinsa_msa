package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;

public interface PayloadGenerator {

	Object generate(String channel, String targetId, ReactionType reactionType, boolean canceled);

	boolean isSupport(TargetEntity targetEntity);
}
