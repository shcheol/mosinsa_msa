package com.mosinsa.order.infra.feignclient;

import java.time.LocalDateTime;

public record CouponResponse(String couponId, String discountPolicy, String promotionId, LocalDateTime issuedDate,
							 String memberId, LocalDateTime duringDate, boolean available) {
}
