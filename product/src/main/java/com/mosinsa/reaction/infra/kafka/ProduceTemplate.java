package com.mosinsa.reaction.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.channel.ChannelProvider;
import com.mosinsa.reaction.infra.kafka.generator.PayloadGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProduceTemplate {

	private final Map<String, ChannelProvider> channelProviderMap;

	private final Map<String, PayloadGenerator> payloadGeneratorMap;

	private final KafkaTemplate<String, String> kafkaTemplate;

	private final ObjectMapper om;

	public void produce(TargetEntity target, String targetId, ReactionType reactionType, boolean canceled) {

		// topic 생성
		String topic = TopicGenerator.generate(target, reactionType);

		//채널
		String channel = channelProviderMap.get(target.name().toLowerCase() + "ChannelProvider").provide(targetId);

		//payload 생성
		Object payload = payloadGeneratorMap.get(target.name().toLowerCase() + "PayloadGenerator").generate(channel, targetId, reactionType, canceled);

		// 전달
		String key = UUID.randomUUID().toString();
		log.info("publish {}, key {}", topic, key);
		kafkaTemplate.send(topic, key, getPayloadFromObject(payload));
	}

	private String getPayloadFromObject(Object event){
		try {
			return om.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}
}
