package com.mosinsa.order.infra.stub;

import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import org.springframework.http.ResponseEntity;

public class StubCouponAdapter implements CouponAdapter {
	@Override
	public ResponseResult<Void> useCoupon(String couponId) {
		System.out.println("use");
		return ResponseResult.executeForResponseEntity(() -> ResponseEntity.ok().build());
	}
}
