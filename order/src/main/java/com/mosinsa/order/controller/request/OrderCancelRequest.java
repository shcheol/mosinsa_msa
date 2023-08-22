package com.mosinsa.order.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCancelRequest {

    private String customerId;
    private Long orderId;

    public OrderCancelRequest(String customerId, Long orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}
