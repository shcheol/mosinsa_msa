package com.mosinsa.order.infra.feignclient.coupon;

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
		if (!inputValidCheck(couponId)) {
			log.debug("order without coupon");
			return;
		}
		couponClient.useCoupon(headers, couponId);
	}

	public void cancelCoupon(Map<String, Collection<String>> headers, String couponId){
		if (!inputValidCheck(couponId)) {
			log.debug("no cancel coupon");
			return;
		}
		couponClient.cancelCoupon(headers, couponId);
	}

	private boolean inputValidCheck(String couponId) {
		return StringUtils.hasText(couponId);
	}
}
