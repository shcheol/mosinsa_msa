package com.mosinsa.order.infra.api.feignclient.product;

public record ProductResponse(String productId, String name, int price,
                              long currentStock, long totalStock, String stockStatus) {
}
