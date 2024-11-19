package com.mosinsa.common.event;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import lombok.Getter;

@Getter
public class ReactionReactedEvent extends Event{
	private TargetEntity target;
	private String targetId;
	private ReactionType reactionType;

	public ReactionReactedEvent(TargetEntity target, String targetId, ReactionType reactionType) {
		this.target = target;
		this.targetId = targetId;
		this.reactionType = reactionType;
	}
}
