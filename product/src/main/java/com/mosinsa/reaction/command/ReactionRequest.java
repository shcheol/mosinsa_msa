package com.mosinsa.reaction.command;

import com.mosinsa.reaction.domain.ReactionType;
import com.mosinsa.reaction.domain.TargetEntity;

public record ReactionRequest(TargetEntity target, String targetId, ReactionType reactionType, String memberId) {
}
