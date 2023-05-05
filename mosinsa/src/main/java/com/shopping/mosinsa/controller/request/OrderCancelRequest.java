package com.shopping.mosinsa.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCancelRequest {

    private Long customerId;
    private Long orderId;

    public OrderCancelRequest(Long customerId, Long orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}
