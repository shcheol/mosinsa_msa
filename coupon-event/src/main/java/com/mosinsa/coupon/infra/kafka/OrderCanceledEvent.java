package com.mosinsa.coupon.infra.kafka;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.coupon.command.domain.DiscountPolicy;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderCanceledEvent(String id, CustomerInfo customerInfo, List<OrderProductInfo> orderProducts) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record OrderProductInfo(String id, List<CouponDto> coupons) {
		public record CouponDto(String id, DiscountPolicy discountPolicy) {

		}
	}
}