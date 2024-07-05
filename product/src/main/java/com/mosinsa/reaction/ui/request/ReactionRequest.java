package com.mosinsa.reaction.ui.request;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;

public record ReactionRequest(TargetEntity target, String targetId, ReactionType reactionType) {
}
