package com.mosinsa.order.infra.feignclient.coupon;

import com.mosinsa.order.infra.feignclient.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponCommandService {

	private final CouponClient couponClient;

	public void useCoupon(Map<String, Collection<String>> headers, String couponId){
		if (verifyCouponInput(couponId)) {
			log.debug("use coupon {}", couponId);
			ResponseResult<CouponResponse> response = ResponseResult.execute(() -> couponClient.useCoupon(headers, couponId));
			return;
		}
		log.debug("order without coupon");
	}

	public void cancelCoupon(Map<String, Collection<String>> headers, String couponId){
		if (verifyCouponInput(couponId)) {
			log.debug("cancel coupon {}", couponId);
			ResponseResult<CouponResponse> response = ResponseResult.execute(() -> couponClient.cancelCoupon(headers, couponId));
			return;
		}
		log.debug("no cancel coupon");
	}

	private boolean verifyCouponInput(String couponId) {
		return StringUtils.hasText(couponId);
	}
}
