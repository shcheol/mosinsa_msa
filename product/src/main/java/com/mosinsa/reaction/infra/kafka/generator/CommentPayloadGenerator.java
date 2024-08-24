package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.events.CommentDislikesEvent;
import com.mosinsa.reaction.infra.kafka.events.CommentLikesEvent;
import org.springframework.stereotype.Component;

@Component
public class CommentPayloadGenerator implements PayloadGenerator {

    @Override
    public Object generate(String channel, String targetId, ReactionType reactionType, boolean canceled) {

        if (ReactionType.LIKES.equals(reactionType)) {
            return new CommentLikesEvent(channel, targetId, canceled);
        }
        if (ReactionType.DISLIKES.equals(reactionType)) {
            return new CommentDislikesEvent(channel, targetId, canceled);
        }
        throw new PayloadGenerateFailException();
    }

    @Override
    public boolean isSupport(TargetEntity targetEntity) {
        return TargetEntity.COMMENT.equals(targetEntity);
    }
}
