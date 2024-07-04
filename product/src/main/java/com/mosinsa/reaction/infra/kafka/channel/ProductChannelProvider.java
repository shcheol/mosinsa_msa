package com.mosinsa.reaction.infra.kafka.channel;

import org.springframework.stereotype.Component;

@Component
public class ProductChannelProvider implements ChannelProvider {

	@Override
	public String provide(String targetId) {
		return targetId;
	}
}
