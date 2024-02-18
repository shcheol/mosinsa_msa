package com.mosinsa.order.infra.feignclient.product;

public record CancelOrderProductRequest(String productId, int quantity) {
}
