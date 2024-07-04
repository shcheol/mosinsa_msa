package com.mosinsa.reaction.infra.kafka;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.infra.kafka.channel.ChannelProvider;
import com.mosinsa.reaction.infra.kafka.generator.PayloadGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProducerService {

	private final Map<String, ChannelProvider> channelProviderMap;

	private final Map<String, PayloadGenerator> payloadGeneratorMap;


	public void produce(TargetEntity target, String targetId, ReactionType reactionType, boolean canceled) {

		// topic 생성
		String topic = TopicGenerator.getTopic(target, reactionType);

		//채널

		String channel = channelProviderMap.get(target.name().toLowerCase() + "ChannelProvider").provide(targetId);

		//payload 생성
		Object payload = payloadGeneratorMap.get(target.name().toLowerCase() + "PayloadGenerator").generate(channel, targetId, reactionType, canceled);

		// 전달
		KafkaEvents.raise(topic, payload);
	}
}
