package com.mosinsa.order.infra.feignclient.coupon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "coupon-service", url = "${feignclient.url.coupon}")
public interface CouponClient {

	@GetMapping("/coupons/{couponId}")
	CouponResponse getCoupon(@RequestHeader Map<String, Collection<String>> headers,
							   @PathVariable(value = "couponId") String couponId);

	@PatchMapping("/coupons/{couponId}")
	CouponResponse useCoupon(@RequestHeader Map<String, Collection<String>> headers,
							 @PathVariable(value = "couponId") String couponId);

	@PatchMapping("/coupons/{couponId}/cancel")
	CouponResponse cancelCoupon(@RequestHeader Map<String, Collection<String>> headers,
							 @PathVariable(value = "couponId") String couponId);

}
