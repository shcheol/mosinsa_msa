package com.mosinsa.order.application;

import com.mosinsa.order.domain.InvalidCouponException;
import com.mosinsa.order.infra.feignclient.CouponClient;
import com.mosinsa.order.infra.feignclient.SimpleCouponResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponQueryService {

	private final CouponClient couponClient;

	public SimpleCouponResponse getCoupon(Map<String, Collection<String>> headers, String couponId) {

		if (!inputValidCheck(couponId)) {
			log.debug("order without coupon");
			return new SimpleCouponResponse("", "", false);
		}
		return SimpleCouponResponse.of(couponClient.getCoupon(headers, couponId));
	}

	private boolean inputValidCheck(String couponId) {
		return StringUtils.hasText(couponId);
	}
}
