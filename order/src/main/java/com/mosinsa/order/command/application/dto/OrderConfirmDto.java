package com.mosinsa.order.command.application.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderConfirmDto(String couponId, String customerId,
							  List<OrderProductDto> orderProducts, ShippingInfoDto shippingInfo, int totalAmount) {
}
