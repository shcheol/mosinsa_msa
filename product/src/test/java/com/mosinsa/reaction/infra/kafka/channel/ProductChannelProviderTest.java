package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.reaction.command.domain.TargetEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductChannelProviderTest {

    @Test
    void provide(){
        ProductChannelProvider productChannelProvider = new ProductChannelProvider();
        String targetID = productChannelProvider.provide("targetId");
        Assertions.assertThat(targetID).isEqualTo("targetId");
    }

    @Test
    void isSupport(){
        ProductChannelProvider provider = new ProductChannelProvider();
        Assertions.assertThat(provider.isSupport(TargetEntity.PRODUCT)).isTrue();
        Assertions.assertThat(provider.isSupport(TargetEntity.REVIEW)).isFalse();
        Assertions.assertThat(provider.isSupport(TargetEntity.COMMENT)).isFalse();
    }
}