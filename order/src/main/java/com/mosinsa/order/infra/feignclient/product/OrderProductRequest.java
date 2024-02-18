package com.mosinsa.order.infra.feignclient.product;

public record OrderProductRequest(String productId, int quantity) {
}
