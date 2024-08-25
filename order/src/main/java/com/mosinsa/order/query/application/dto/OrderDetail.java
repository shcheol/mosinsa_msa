package com.mosinsa.order.query.application.dto;

import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderStatus;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class OrderDetail {

	String orderId;
	String customerId;
	String couponId;
	int totalPrice;
	OrderStatus status;
	ShippingInfoDto shippingInfo;
	List<OrderProductDto> orderProducts = new ArrayList<>();

	public OrderDetail(Order order) {
		this.orderId = order.getId().getId();
		this.customerId = order.getCustomerId();
		this.couponId = order.getOrderCoupon().getCouponId();
		this.status = order.getStatus();
		this.totalPrice = order.getTotalPrice().getValue();
		this.shippingInfo = ShippingInfoDto.of(order.getShippingInfo());
		this.orderProducts.addAll(order.getOrderProducts().stream().map(OrderProductDto::of).toList());

	}

}
