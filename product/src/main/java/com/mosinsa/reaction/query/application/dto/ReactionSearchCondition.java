package com.mosinsa.reaction.query.application.dto;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;

public record ReactionSearchCondition(TargetEntity target, String targetId, ReactionType reactionType, String memberId) {
}
