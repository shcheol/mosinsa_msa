package com.mosinsa.promotion.infra.api.httpinterface;

public record OrderSummary(String orderId, String customerId, String couponId, String status, Long totalPrice) {
}
