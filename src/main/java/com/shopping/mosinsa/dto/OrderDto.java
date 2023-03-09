package com.shopping.mosinsa.dto;

import com.shopping.mosinsa.entity.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class OrderDto {

    private Long id;

    private Long customerId;

    private final List<OrderProductDto> orderProducts = new ArrayList<>();

    private int totalPrice;

    private OrderStatus status;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomer().getId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();

        List<OrderProduct> orderProductList = order.getOrderProducts();
        for (OrderProduct orderProduct : orderProductList) {
            orderProducts.add(new OrderProductDto(orderProduct));
        }
    }
}
