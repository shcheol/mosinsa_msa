package com.mosinsa.reaction.infra.kafka.channel;

import org.springframework.stereotype.Component;

@Component
public interface ChannelProvider {
	String provide(String targetId);
}
