package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.reaction.command.domain.TargetEntity;
import org.springframework.stereotype.Component;

@Component
public interface ChannelProvider {
	String provide(String targetId);

	boolean isSupport(TargetEntity targetEntity);
}
