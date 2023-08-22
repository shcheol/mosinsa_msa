package com.mosinsa.order.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private String customerId;
    private Map<String, Integer> products = new ConcurrentHashMap<>();

    public OrderCreateRequest(String customerId, Map<String, Integer> products) {
        this.customerId = customerId;
        this.products = products;
    }
}
