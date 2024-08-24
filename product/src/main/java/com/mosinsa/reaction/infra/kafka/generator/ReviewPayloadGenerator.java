package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.events.ReviewDislikesEvent;
import com.mosinsa.reaction.infra.kafka.events.ReviewLikesEvent;
import org.springframework.stereotype.Component;

@Component
public class ReviewPayloadGenerator implements PayloadGenerator {
    @Override
    public Object generate(String channel, String targetId, ReactionType reactionType, boolean canceled) {
        if (ReactionType.LIKES.equals(reactionType)) {
            return new ReviewLikesEvent(channel, targetId, canceled);
        }
        if (ReactionType.DISLIKES.equals(reactionType)) {
            return new ReviewDislikesEvent(channel, targetId, canceled);
        }
        throw new PayloadGenerateFailException();
    }

    @Override
    public boolean isSupport(TargetEntity targetEntity) {
        return TargetEntity.REVIEW.equals(targetEntity);
    }
}
