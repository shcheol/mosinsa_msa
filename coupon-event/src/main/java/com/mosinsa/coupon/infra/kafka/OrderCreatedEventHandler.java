package com.mosinsa.coupon.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.coupon.application.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventHandler {

    private final CouponService couponService;

	@KafkaListener(topics = "${mosinsa.topic.order.create}")
	public void orderCreatedEvent(String message) throws JsonProcessingException {
		OrderCreatedEvent orderCreatedEvent = new ObjectMapper().readValue(message, OrderCreatedEvent.class);

		couponService.usedCoupon(orderCreatedEvent.couponId());
	}
}
