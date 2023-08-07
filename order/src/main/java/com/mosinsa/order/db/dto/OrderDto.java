package com.mosinsa.order.db.dto;

import com.mosinsa.order.db.entity.Order;
import com.mosinsa.order.db.entity.OrderStatus;
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
    private Long customerId;
    private int totalPrice;
    private OrderStatus status;
    private final List<OrderProductDto> orderProducts = new ArrayList<>();

    public OrderDto(Long orderId, Long customerId, int totalPrice, OrderStatus orderStatus, List<OrderProductDto> orderProducts) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.status = orderStatus;
        this.orderProducts.addAll(orderProducts);
    }

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.customerId = order.getCustomerId();
        this.status = order.getStatus();
    }

}
