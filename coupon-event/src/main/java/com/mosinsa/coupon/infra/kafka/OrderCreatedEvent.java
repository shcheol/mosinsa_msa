package com.mosinsa.coupon.infra.kafka;

public record OrderCreatedEvent(String orderId, String couponId) {
}
