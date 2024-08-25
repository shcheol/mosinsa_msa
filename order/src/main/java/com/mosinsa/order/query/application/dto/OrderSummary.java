package com.mosinsa.order.query.application.dto;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderCoupon;
import com.mosinsa.order.command.domain.OrderStatus;
import lombok.Value;

@Value
public class OrderSummary {

    String orderId;
    String customerId;
	String couponId;
    int totalPrice;
    OrderStatus status;

    public OrderSummary(Order order) {
        this.orderId = order.getId().getId();
        this.customerId = order.getCustomerId();
		this.couponId = order.getOrderCoupon().getCouponId();
        this.status = order.getStatus();
		this.totalPrice = order.getTotalPrice().getValue();
    }

}
