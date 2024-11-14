package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mosinsa.common.argumentresolver.CustomerInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderCanceledEvent(String id, CustomerInfo customerInfo, List<OrderProductInfo> orderProducts) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record OrderProductInfo(String id, int quantity, String name, List<ProductOptionsDto> options) {
		public record ProductOptionsDto(Long id, String name) {

		}
	}
}