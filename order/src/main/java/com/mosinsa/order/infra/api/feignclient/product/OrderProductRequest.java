package com.mosinsa.order.infra.api.feignclient.product;

public record OrderProductRequest(String productId, int quantity) {
}
