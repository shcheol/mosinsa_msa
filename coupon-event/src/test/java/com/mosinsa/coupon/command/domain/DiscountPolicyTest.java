package com.mosinsa.coupon.command.domain;

import com.mosinsa.coupon.command.domain.DiscountPolicy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

	@Test
	void discount(){
		assertThat(DiscountPolicy.NONE.applyDiscountPrice(1000)).isZero();
		assertThat(DiscountPolicy.PERCENT_10.applyDiscountPrice(1000)).isEqualTo(100);
		assertThat(DiscountPolicy.PERCENT_20.applyDiscountPrice(1000)).isEqualTo(200);
	}

}