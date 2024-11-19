package com.mosinsa.common.event;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;

public record ReactionEvent(String channelId, TargetEntity target, String targetId, ReactionType reactionType, boolean canceled) {
}
