package com.shopping.mosinsa.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private Long customerId;
    private Map<Long, Integer> orderProducts = new ConcurrentHashMap<>();

    public OrderCreateRequest(Long customerId, Map<Long, Integer> orderProducts) {
        this.customerId = customerId;
        this.orderProducts = orderProducts;
    }
}
