package com.mosinsa.order.ui.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCancelRequest {

    private String customerId;
    private String orderId;

    public OrderCancelRequest(String customerId, String orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}
