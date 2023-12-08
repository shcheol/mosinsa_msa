package com.mosinsa.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private String customerId;
    private int totalPrice;
    private String status;
    private final List<OrderProductDto> orderProducts = new ArrayList<>();

    public OrderDto(Long orderId, String customerId, int totalPrice, String orderStatus, List<OrderProductDto> orderProducts) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.status = orderStatus;

        this.orderProducts.addAll(orderProducts);

    }

}
