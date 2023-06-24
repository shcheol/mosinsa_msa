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

    private Long id;
    private Long customerId;
    private int totalPrice;
    private String status;
    private final List<OrderProductDto> orderProducts = new ArrayList<>();

    public OrderDto(Long id, Long customerId, int totalPrice, String orderStatus,List<OrderProductDto> orderProducts) {
        this.id = id;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.status = orderStatus;

        this.orderProducts.addAll(orderProducts);

    }

}
