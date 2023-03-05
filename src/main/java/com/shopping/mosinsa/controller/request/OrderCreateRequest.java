package com.shopping.mosinsa.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private Long customerId;

    public OrderCreateRequest(Long customerId) {
        this.customerId = customerId;
    }
}
