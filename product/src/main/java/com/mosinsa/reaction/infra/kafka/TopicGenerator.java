package com.mosinsa.reaction.infra.kafka;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;

public class TopicGenerator {

	private static final String PREFIX = "mosinsa-";

	private TopicGenerator() {
	}

	public static String generate(TargetEntity target, ReactionType reactionType){
		if (target == null || reactionType == null){
			throw new TopicGenerateFailException();
		}
		return PREFIX +target.name().toLowerCase() + "-"+reactionType.name().toLowerCase();
	}
}
