package com.mosinsa.order.infra.feignclient;

public record OrderProductRequest(String productId, int quantity) {
}
