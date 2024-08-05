package com.mosinsa.coupon.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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