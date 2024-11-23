package com.mosinsa.common.event;

import com.mosinsa.reaction.infra.kafka.ProduceTemplate;
import com.mosinsa.reaction.infra.kafka.TopicInfo;
import com.mosinsa.reaction.infra.kafka.channel.ChannelProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReactionEventHandler {

	private final List<ChannelProvider> channelProviders;
	private final ProduceTemplate produceTemplate;
	private final TopicInfo topicInfo;

	@Async
	@TransactionalEventListener(classes = ReactionReactedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void reactionReactedHandler(ReactionReactedEvent event) {
		String topic = topicInfo.reaction().get("react");


		String channel = channelProviders.stream()
				.filter(channelProvider -> channelProvider.isSupport(event.getTarget()))
				.findAny()
				.orElseThrow()
				.provide(event.getTargetId());

		produceTemplate.produce(topic, new ReactionEvent(channel, event.getTarget(), event.getTargetId(), event.getReactionType(), false));
	}

	@Async
	@TransactionalEventListener(classes = ReactionCanceledEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void reactionCanceledHandler(ReactionCanceledEvent event) {
		String topic = topicInfo.reaction().get("cancel");

		String channel = channelProviders.stream()
				.filter(channelProvider -> channelProvider.isSupport(event.getTarget()))
				.findAny().orElseThrow()
				.provide(event.getTargetId());

		produceTemplate.produce(topic, new ReactionEvent(channel, event.getTarget(), event.getTargetId(), event.getReactionType(), true));
	}
}
