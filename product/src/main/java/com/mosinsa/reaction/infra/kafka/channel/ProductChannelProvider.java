package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.reaction.command.domain.TargetEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductChannelProvider implements ChannelProvider {

	@Override
	public String provide(String targetId) {
		return targetId;
	}

	@Override
	public boolean isSupport(TargetEntity targetEntity) {
		return TargetEntity.PRODUCT.equals(targetEntity);
	}
}
