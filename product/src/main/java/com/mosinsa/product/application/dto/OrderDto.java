package com.mosinsa.product.application.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Value
public class OrderDto {

    Long orderId;
    String customerId;
    int totalPrice;
    String status;
    List<OrderProductDto> orderProducts = new ArrayList<>();

    public OrderDto(Long orderId, String customerId, int totalPrice, String orderStatus, List<OrderProductDto> orderProducts) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.status = orderStatus;

        this.orderProducts.addAll(orderProducts);
    }

}
