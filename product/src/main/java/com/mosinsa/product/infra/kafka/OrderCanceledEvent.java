package com.mosinsa.product.infra.kafka;

import java.util.List;

public record OrderCanceledEvent(String orderId, String customerId, String couponId, List<OrderedProduct> orderProducts) {

	record OrderedProduct(String productId, int price, int quantity, int amounts){

	}
}
