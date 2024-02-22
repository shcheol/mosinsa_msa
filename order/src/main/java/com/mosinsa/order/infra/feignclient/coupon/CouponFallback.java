package com.mosinsa.order.infra.feignclient.coupon;

import com.mosinsa.order.infra.feignclient.CouponServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class CouponFallback implements CouponClient {
	@Override
	public CouponResponse getCoupon(Map<String, Collection<String>> headers, String couponId) {
		log.warn("coupon service not available, try later or order with no coupon");
		throw new CouponServiceNotAvailableException("coupon service not available, try later or order with no coupon");
	}

	@Override
	public CouponResponse useCoupon(Map<String, Collection<String>> headers, String couponId) {
		log.warn("coupon service not available, try later or order with no coupon");
		throw new CouponServiceNotAvailableException("coupon service not available, try later or order with no coupon");
	}

	@Override
	public CouponResponse cancelCoupon(Map<String, Collection<String>> headers, String couponId) {
		log.warn("coupon service not available, save fail request batch");
		//TODO: save cancel request in db
		return null;
	}
}
