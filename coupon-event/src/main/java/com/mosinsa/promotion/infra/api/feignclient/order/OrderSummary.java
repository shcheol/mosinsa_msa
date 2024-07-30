package com.mosinsa.promotion.infra.api.feignclient.order;

public record OrderSummary(String orderId, String customerId, String couponId, String status, Long totalPrice) {
}
