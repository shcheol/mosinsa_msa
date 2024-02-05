package com.mosinsa.order.dto;

import com.mosinsa.order.domain.Order;
import com.mosinsa.order.domain.OrderStatus;
import lombok.Value;

@Value
public class OrderDto {

    String orderId;
    String customerId;
	String couponId;
    int totalPrice;
    OrderStatus status;

    public OrderDto(Order order) {
        this.orderId = order.getId().getId();
        this.customerId = order.getCustomerId();
		this.couponId = order.getCouponId();
        this.status = order.getStatus();
		this.totalPrice = order.getTotalPrice().getValue();
    }

}
