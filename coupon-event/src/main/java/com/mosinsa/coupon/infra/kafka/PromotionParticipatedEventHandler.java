package com.mosinsa.coupon.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.coupon.command.application.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromotionParticipatedEventHandler {

	private final CouponService couponService;

	private final ObjectMapper om;

	@KafkaListener(topics = "${mosinsa.topic.promotion.participate}")
	public void promotionParticipatedEvent(String message) throws JsonProcessingException {
		log.info("promotionParticipatedEvent: {}",message);
		ParticipatedEvent participatedEvent = om.readValue(message, ParticipatedEvent.class);

		couponService.issue(participatedEvent.memberId(), participatedEvent.couponGroupSequence());
	}
}
