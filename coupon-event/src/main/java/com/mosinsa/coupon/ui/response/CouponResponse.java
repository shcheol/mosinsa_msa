package com.mosinsa.coupon.ui.response;

import com.mosinsa.coupon.domain.CouponState;
import com.mosinsa.coupon.application.dto.CouponDto;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CouponResponse {

	String couponId;
	String discountPolicy;
	String promotionId;
	LocalDateTime issuedDate;
	String memberId;
	LocalDateTime duringDate;
	boolean available;

	public CouponResponse(CouponDto couponDto) {
		this.couponId = couponDto.getCouponId();
		this.discountPolicy = couponDto.getDetails().getDiscountPolicy().toString();
		this.promotionId = couponDto.getPromotionId();
		this.issuedDate = couponDto.getIssuedDate();
		this.memberId = couponDto.getMemberId();
		this.duringDate = couponDto.getDetails().getDuringDate();
		this.available = couponDto.getState().equals(CouponState.ISSUED) && LocalDateTime.now().isBefore(this.duringDate);
	}
}
