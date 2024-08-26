package com.mosinsa.order.infra.api.feignclient.coupon;

import com.mosinsa.order.infra.api.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponFeignAdapterTest {

	@Autowired
	CouponFeignAdapter adapter;

	@Test
	void useCouponEmpty() {
		assertThat(adapter.useCoupon("")).isEqualTo(ResponseResult.empty());
		assertThat(adapter.useCoupon(null)).isEqualTo(ResponseResult.empty());
	}

	@Test
	void getCouponEmpty() {
		assertThat(adapter.getCoupon("")).isEqualTo(ResponseResult.empty());
		assertThat(adapter.getCoupon(null)).isEqualTo(ResponseResult.empty());
	}
}