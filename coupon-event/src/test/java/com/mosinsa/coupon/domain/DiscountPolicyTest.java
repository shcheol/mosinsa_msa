package com.mosinsa.coupon.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

	@Test
	void discount(){
		assertThat(DiscountPolicy.NONE.applyDiscountPrice(1000)).isZero();
		assertThat(DiscountPolicy.TEN_PERCENTAGE.applyDiscountPrice(1000)).isEqualTo(100);
		assertThat(DiscountPolicy.TWENTY_PERCENTAGE.applyDiscountPrice(1000)).isEqualTo(200);
	}

}