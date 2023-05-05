package com.shopping.mosinsa.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private Long customerId;
    private Map<Long, Integer> products = new ConcurrentHashMap<>();

    public OrderCreateRequest(Long customerId, Map<Long, Integer> products) {
        this.customerId = customerId;
        this.products = products;
    }
}
