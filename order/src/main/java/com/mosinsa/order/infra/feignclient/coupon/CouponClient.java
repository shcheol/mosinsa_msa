package com.mosinsa.order.infra.feignclient.coupon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "coupon-service", url = "${feignclient.url.coupon}", fallback = CouponFallback.class, dismiss404 = true)
public interface CouponClient {

	@GetMapping("/coupons/{couponId}")
	CouponResponse getCoupon(@RequestHeader Map<String, Collection<String>> headers,
							   @PathVariable(value = "couponId") String couponId);

	@PostMapping("/coupons/{couponId}")
	CouponResponse useCoupon(@RequestHeader Map<String, Collection<String>> headers,
							 @PathVariable(value = "couponId") String couponId);

	@PostMapping("/coupons/{couponId}/cancel")
	CouponResponse cancelCoupon(@RequestHeader Map<String, Collection<String>> headers,
							 @PathVariable(value = "couponId") String couponId);

}
