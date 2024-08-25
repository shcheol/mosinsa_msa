package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.events.ReviewDislikesEvent;
import com.mosinsa.reaction.infra.kafka.events.ReviewLikesEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewPayloadGeneratorTest {
    @Test
    void generate() {
        ReviewPayloadGenerator reviewPayloadGenerator = new ReviewPayloadGenerator();
        assertThat(reviewPayloadGenerator.generate("channel", "targetId", ReactionType.LIKES, true))
                .isEqualTo(new ReviewLikesEvent("channel","targetId",true));
        assertThat(reviewPayloadGenerator.generate("channel", "targetId", ReactionType.DISLIKES, true))
                .isEqualTo(new ReviewDislikesEvent("channel","targetId",true));
    }

    @Test
    void generateFail() {
        ReviewPayloadGenerator reviewPayloadGenerator = new ReviewPayloadGenerator();

        assertThrows(PayloadGenerateFailException.class,
                () -> reviewPayloadGenerator.generate("channel", "targetId", null, true));
    }

    @Test
    void isSupport(){
        ReviewPayloadGenerator reviewPayloadGenerator = new ReviewPayloadGenerator();
        Assertions.assertThat(reviewPayloadGenerator.isSupport(TargetEntity.PRODUCT)).isFalse();
        Assertions.assertThat(reviewPayloadGenerator.isSupport(TargetEntity.REVIEW)).isTrue();
        Assertions.assertThat(reviewPayloadGenerator.isSupport(TargetEntity.COMMENT)).isFalse();
    }

}