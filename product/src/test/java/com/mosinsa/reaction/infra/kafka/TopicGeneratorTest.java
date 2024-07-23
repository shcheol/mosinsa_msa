package com.mosinsa.reaction.infra.kafka;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TopicGeneratorTest {

    @Test
    @DisplayName("토픽생성")
    void getTopic() {

        assertThat(TopicGenerator.generate(TargetEntity.COMMENT, ReactionType.DISLIKES))
                .isEqualTo("mosinsa-comment-dislikes");
        assertThat(TopicGenerator.generate(TargetEntity.COMMENT, ReactionType.LIKES))
                .isEqualTo("mosinsa-comment-likes");
        assertThat(TopicGenerator.generate(TargetEntity.REVIEW, ReactionType.DISLIKES))
                .isEqualTo("mosinsa-review-dislikes");
        assertThat(TopicGenerator.generate(TargetEntity.REVIEW, ReactionType.LIKES))
                .isEqualTo("mosinsa-review-likes");
        assertThat(TopicGenerator.generate(TargetEntity.PRODUCT, ReactionType.DISLIKES))
                .isEqualTo("mosinsa-product-dislikes");
        assertThat(TopicGenerator.generate(TargetEntity.PRODUCT, ReactionType.LIKES))
                .isEqualTo("mosinsa-product-likes");
    }

    @Test
    @DisplayName("토픽생성실패")
    void getTopicFail() {

        assertThrows(TopicGenerateFailException.class, () -> TopicGenerator.generate(TargetEntity.COMMENT, null));
        assertThrows(TopicGenerateFailException.class, () -> TopicGenerator.generate(null, ReactionType.LIKES));
        assertThrows(TopicGenerateFailException.class, () -> TopicGenerator.generate(null, null));

    }
}