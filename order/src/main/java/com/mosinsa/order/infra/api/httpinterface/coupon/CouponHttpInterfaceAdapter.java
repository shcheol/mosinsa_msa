package com.mosinsa.order.infra.api.httpinterface.coupon;

import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponHttpInterfaceAdapter implements CouponAdapter {

	private final CouponHttpClient couponHttpClient;
	@Override
	public ResponseResult<Void> useCoupon(String couponId) {
		return ResponseResult.executeForResponseEntity(() -> couponHttpClient.useCoupon(couponId));
	}
}
