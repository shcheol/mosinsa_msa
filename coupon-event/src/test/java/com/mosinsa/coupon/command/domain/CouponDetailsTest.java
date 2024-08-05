package com.mosinsa.coupon.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CouponDetailsTest {

	LocalDateTime date = LocalDateTime.of(2023, 10, 28, 00, 00);

	@Test
	void equalsAndHashCode(){
		CouponDetails couponDetails1 = CouponDetails.of(date, DiscountPolicy.NONE);
		CouponDetails couponDetails2 = CouponDetails.of(date, DiscountPolicy.NONE);
		CouponDetails couponDetails3 = CouponDetails.of(date.plusDays(3), DiscountPolicy.NONE);
		CouponDetails couponDetails4 = CouponDetails.of(date, DiscountPolicy.TEN_PERCENTAGE);
		CouponDetails couponDetails = new CouponDetails();

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(couponDetails1, couponDetails2, couponDetails, couponDetails3, couponDetails4);
		assertThat(b).isTrue();
	}
}