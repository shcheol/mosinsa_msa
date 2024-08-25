package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.reaction.command.domain.TargetEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CommentChannelProviderTest {

    @Autowired
    CommentChannelProvider provider;

    @Test
    void provide() {
        String commentId1 = provider.provide("commentId1");
        Assertions.assertThat(commentId1).isEqualTo("reviewId1");
    }

    @Test
    void provideFail() {
        assertThrows(ReviewException.class, () -> provider.provide("commentId1xxx"));
    }

    @Test
    void isSupport(){
        Assertions.assertThat(provider.isSupport(TargetEntity.PRODUCT)).isFalse();
        Assertions.assertThat(provider.isSupport(TargetEntity.REVIEW)).isFalse();
        Assertions.assertThat(provider.isSupport(TargetEntity.COMMENT)).isTrue();
    }
}