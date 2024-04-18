package com.mosinsa.order.command.application.dto;

import com.mosinsa.order.command.domain.OrderProduct;
import lombok.Builder;
import lombok.Value;

@Builder
public record OrderProductDto(String productId, int price, int quantity, int amounts) {
	public static OrderProductDto of(OrderProduct orderProduct) {
		return new OrderProductDto(
				orderProduct.getProductId(),
				orderProduct.getPrice().getValue(),
				orderProduct.getQuantity(),
				orderProduct.getAmounts().getValue());
	}
}
