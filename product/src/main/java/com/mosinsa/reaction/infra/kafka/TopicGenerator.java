package com.mosinsa.reaction.infra.kafka;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;

public class TopicGenerator {

	private static final String PREFIX = "mosinsa-";

	public static String getTopic(TargetEntity target, ReactionType reactionType){

		return PREFIX +target.name().toLowerCase() + "-"+reactionType.name().toLowerCase();
	}
}
