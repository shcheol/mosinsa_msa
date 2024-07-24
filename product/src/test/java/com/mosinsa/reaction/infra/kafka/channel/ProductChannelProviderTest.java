package com.mosinsa.reaction.infra.kafka.channel;

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
}