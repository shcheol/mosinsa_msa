package com.mosinsa.order.infra.api.feignclient.coupon;

import com.mosinsa.order.ApplicationTest;
import com.mosinsa.order.infra.api.ResponseResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CouponFeignAdapterTest extends ApplicationTest {

	@Autowired
	CouponFeignAdapter adapter;

	@Test
	void useCouponEmpty() {
		assertThat(adapter.useCoupon("")).isEqualTo(ResponseResult.empty());
		assertThat(adapter.useCoupon(null)).isEqualTo(ResponseResult.empty());
	}

	@Test
	void getCoupon() {
		when(couponClient.getCoupon(any(), any()))
				.thenReturn(new CouponResponse("couponId", null, null, null, null, null, false));

		ResponseResult<CouponResponse> response = adapter.getCoupon("couponId");
		Assertions.assertThat(response.getStatus()).isEqualTo(200);
		Assertions.assertThat(response.get().couponId()).isEqualTo("couponId");
	}

	@Test
	void useCoupon() {
		doNothing().when(couponClient).useCoupon(any(), any());

		ResponseResult<Void> response = adapter.useCoupon("couponId");
		Assertions.assertThat(response.getStatus()).isEqualTo(200);
	}

	@Test
	void getCouponEmpty() {
		assertThat(adapter.getCoupon("")).isEqualTo(ResponseResult.empty());
		assertThat(adapter.getCoupon(null)).isEqualTo(ResponseResult.empty());
	}
}