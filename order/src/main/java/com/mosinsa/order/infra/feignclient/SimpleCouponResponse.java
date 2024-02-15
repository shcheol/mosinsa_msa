package com.mosinsa.order.infra.feignclient;

public record SimpleCouponResponse(String couponId, String discountPolicy, boolean available) {

	public static SimpleCouponResponse of(CouponResponse response){
		return new SimpleCouponResponse(response.couponId(), response.discountPolicy(), response.available());
	}
}
