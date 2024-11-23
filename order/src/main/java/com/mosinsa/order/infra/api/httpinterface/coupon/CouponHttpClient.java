package com.mosinsa.order.infra.api.httpinterface.coupon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/coupon-service")
public interface CouponHttpClient {

	@PostExchange("/coupons/{couponId}")
	ResponseEntity<Void> useCoupon(@PathVariable(value = "couponId") String couponId);
}
