package com.mosinsa.coupon.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.coupon.command.application.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCanceledEventHandler {

    private final CouponService couponService;

	private final ObjectMapper om;

	@KafkaListener(topics = "${mosinsa.topic.order.cancel}")
	public void orderCanceledEvent(String message) throws JsonProcessingException {
		OrderCanceledEvent orderCanceledEvent = om.readValue(message, OrderCanceledEvent.class);

		String couponId = orderCanceledEvent.couponId();
		if (StringUtils.hasText(couponId)){
			couponService.cancelCoupon(couponId);
		}
	}
}
