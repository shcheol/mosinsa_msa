package com.mosinsa.order.infra.kafka;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.order.command.domain.DiscountPolicy;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderCanceledEvent(String id, CustomerInfo customerInfo, List<OrderProductInfo> orderProducts) {

	public record OrderProductInfo(String id, String name, int quantity, int perPrice, int totalPrice,
								   List<ProductOptionsDto> options,
								   List<CouponDto> coupons) {
		public record ProductOptionsDto(Long id, String name) {

		}

		public record CouponDto(String id, DiscountPolicy discountPolicy) {

		}
	}
}