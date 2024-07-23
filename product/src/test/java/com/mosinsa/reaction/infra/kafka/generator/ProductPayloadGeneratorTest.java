package com.mosinsa.reaction.infra.kafka.generator;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.infra.kafka.events.ProductLikesEvent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductPayloadGeneratorTest {
    @Test
    void generate() {
        ProductPayloadGenerator productPayloadGenerator = new ProductPayloadGenerator();
        assertThat(productPayloadGenerator.generate("channel", "targetId", ReactionType.LIKES, true))
                .isEqualTo(new ProductLikesEvent("channel",true));
    }

    @Test
    void generateFail() {
        ProductPayloadGenerator productPayloadGenerator = new ProductPayloadGenerator();
        assertThrows(PayloadGenerateFailException.class,
                () -> productPayloadGenerator.generate("channel", "targetId", ReactionType.DISLIKES, true));
        assertThrows(PayloadGenerateFailException.class,
                () -> productPayloadGenerator.generate("channel", "targetId", null, true));
    }

}