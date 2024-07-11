package com.mosinsa.order.infra.api.feignclient.product;

public record CancelOrderProductRequest(String productId, int quantity) {
}
