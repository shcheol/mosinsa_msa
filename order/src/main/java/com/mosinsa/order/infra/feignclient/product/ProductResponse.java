package com.mosinsa.order.infra.feignclient.product;

public record ProductResponse(String productId, String name, int price, int stock, long likes) {

}
