package com.mosinsa.reaction.infra.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.channel.ChannelProvider;
import com.mosinsa.reaction.infra.kafka.generator.PayloadGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProduceTemplate {

    private final List<ChannelProvider> channelProviders;

    private final List<PayloadGenerator> payloadGenerators;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper om;

    public void produce(TargetEntity target, String targetId, ReactionType reactionType, boolean canceled) {

        String topic = TopicGenerator.generate(target, reactionType);

        String channel = channelProviders.stream()
                .filter(channelProvider -> channelProvider.isSupport(target))
                .findAny().orElseThrow()
                .provide(targetId);

        Object payload = payloadGenerators.stream()
                .filter(channelProvider -> channelProvider.isSupport(target))
                .findAny().orElseThrow()
                .generate(channel, targetId, reactionType, canceled);

        String key = UUID.randomUUID().toString();
        log.info("publish {}, key {}", topic, key);
        kafkaTemplate.send(topic, key, getPayloadFromObject(payload));
    }

    protected String getPayloadFromObject(Object event) {
        try {
            return om.writeValueAsString(event);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
