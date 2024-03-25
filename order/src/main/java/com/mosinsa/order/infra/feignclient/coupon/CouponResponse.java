package com.mosinsa.order.infra.feignclient.coupon;

import java.time.LocalDateTime;

public record CouponResponse(String couponId, String discountPolicy, String promotionId, LocalDateTime issuedDate,
							 String memberId, LocalDateTime duringDate, boolean available) {

	public static CouponResponse empty(){
		return new CouponResponse("","","",null,"",null,false);
	}
}
