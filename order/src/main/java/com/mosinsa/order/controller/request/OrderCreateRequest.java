package com.mosinsa.order.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private Long customerId;
    private Map<String, Integer> products = new ConcurrentHashMap<>();

    public OrderCreateRequest(Long customerId, Map<String, Integer> products) {
        this.customerId = customerId;
        this.products = products;
    }
}
