package com.mosinsa.order.infra.kafka;

public record OrderCanceledEvent(String orderId, String couponId) {
}
