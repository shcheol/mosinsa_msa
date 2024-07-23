package com.mosinsa.reaction;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.ProduceTemplate;
import com.mosinsa.reaction.infra.kafka.channel.ChannelProvider;
import com.mosinsa.reaction.infra.kafka.generator.PayloadGenerator;

import java.util.Map;

public class ProduceTemplateStub extends ProduceTemplate {
    public ProduceTemplateStub(Map<String, ChannelProvider> channelProviderMap, Map<String, PayloadGenerator> payloadGeneratorMap) {
        super(channelProviderMap, payloadGeneratorMap);
    }

    @Override
    public void produce(TargetEntity target, String targetId, ReactionType reactionType, boolean canceled) {

    }
}
