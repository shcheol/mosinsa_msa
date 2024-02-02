package com.mosinsa.coupon.infra.kafka;

public record OrderCanceledEvent(String orderId, String couponId) {
}
