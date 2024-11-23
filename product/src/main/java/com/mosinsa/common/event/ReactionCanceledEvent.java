package com.mosinsa.common.event;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import lombok.Getter;

@Getter
public class ReactionCanceledEvent extends Event {
	private final TargetEntity target;
	private final String targetId;
	private final ReactionType reactionType;

	public ReactionCanceledEvent(TargetEntity target, String targetId, ReactionType reactionType) {
		this.target = target;
		this.targetId = targetId;
		this.reactionType = reactionType;
	}
}
