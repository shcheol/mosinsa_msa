package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.events.CommentDislikesEvent;
import com.mosinsa.reaction.infra.kafka.events.CommentLikesEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentPayloadGeneratorTest {

    @Test
    void generate() {
        CommentPayloadGenerator commentPayloadGenerator = new CommentPayloadGenerator();
        assertThat(commentPayloadGenerator.generate("channel", "targetId", ReactionType.LIKES, true))
                .isEqualTo(new CommentLikesEvent("channel","targetId",true));
        assertThat(commentPayloadGenerator.generate("channel", "targetId", ReactionType.DISLIKES, true))
                .isEqualTo(new CommentDislikesEvent("channel","targetId",true));
    }

    @Test
    void generateFail() {
        CommentPayloadGenerator commentPayloadGenerator = new CommentPayloadGenerator();

        assertThrows(PayloadGenerateFailException.class,
                () -> commentPayloadGenerator.generate("channel", "targetId", null, true));
    }

    @Test
    void isSupport(){
        CommentPayloadGenerator commentPayloadGenerator = new CommentPayloadGenerator();
        Assertions.assertThat(commentPayloadGenerator.isSupport(TargetEntity.PRODUCT)).isFalse();
        Assertions.assertThat(commentPayloadGenerator.isSupport(TargetEntity.REVIEW)).isFalse();
        Assertions.assertThat(commentPayloadGenerator.isSupport(TargetEntity.COMMENT)).isTrue();
    }
}