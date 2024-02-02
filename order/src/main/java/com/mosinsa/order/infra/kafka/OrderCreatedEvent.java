package com.mosinsa.order.infra.kafka;

public record OrderCreatedEvent(String orderId, String couponId) {
}
