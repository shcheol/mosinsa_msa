package com.mosinsa.product.ui.request;

import java.util.List;

public record OrderProductRequests(String orderId, List<OrderProductDto> orderProducts) {

	public record OrderProductDto(String id, int quantity, String name, List<ProductOptionsDto> options) {
		public record ProductOptionsDto(Long id, String name) {

		}
	}
}
