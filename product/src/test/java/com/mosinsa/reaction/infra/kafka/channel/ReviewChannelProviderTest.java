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
class ReviewChannelProviderTest {

    @Autowired
    ReviewChannelProvider provider;

    @Test
    void provide() {
        String reviewId1 = provider.provide("reviewId1");
        Assertions.assertThat(reviewId1).isEqualTo("productId1");
    }

    @Test
    void provideFail() {
        assertThrows(ReviewException.class, () -> provider.provide("reviewId1xxx"));
    }
    @Test
    void isSupport(){
        Assertions.assertThat(provider.isSupport(TargetEntity.PRODUCT)).isFalse();
        Assertions.assertThat(provider.isSupport(TargetEntity.REVIEW)).isTrue();
        Assertions.assertThat(provider.isSupport(TargetEntity.COMMENT)).isFalse();
    }
}