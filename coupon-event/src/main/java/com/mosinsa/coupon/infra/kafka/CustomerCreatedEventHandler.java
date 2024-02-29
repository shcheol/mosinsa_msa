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
public class CustomerCreatedEventHandler {

    private final CouponService couponService;

    @KafkaListener(topics = "${mosinsa.topic.customer.create}")
    public void customerCreatedEvent(String message) throws JsonProcessingException {
        log.info("consume: {}", message);
        CustomerCreatedEvent customerCreatedEvent = new ObjectMapper().readValue(message, CustomerCreatedEvent.class);
        log.info("customerCreatedEvent: {}", customerCreatedEvent);

        couponService.createForNewMember(customerCreatedEvent.customerId());
    }
}
