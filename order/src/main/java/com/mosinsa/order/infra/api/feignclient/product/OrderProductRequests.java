package com.mosinsa.order.infra.api.feignclient.product;

import java.util.List;

public record OrderProductRequests(String orderId, List<OrderProductDto> orderProducts) {

	public record OrderProductDto(String id, int quantity, List<ProductOptionsDto> options) {
		public record ProductOptionsDto(Long id, String name) {

		}
	}
}
