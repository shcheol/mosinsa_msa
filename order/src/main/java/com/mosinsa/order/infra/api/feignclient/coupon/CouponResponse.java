package com.mosinsa.order.infra.api.feignclient.coupon;

import com.mosinsa.order.command.domain.DiscountPolicy;

import java.time.LocalDateTime;

public record CouponResponse(String couponId, DiscountPolicy discountPolicy, String promotionId, LocalDateTime issuedDate,
							 String memberId, LocalDateTime duringDate, boolean available) {

	public static CouponResponse empty(){
		return new CouponResponse("",null,"",null,"",null,false);
	}
}
