package com.mosinsa.order.infra.feignclient;

public record CancelOrderProductRequest(String productId, int quantity) {
}
