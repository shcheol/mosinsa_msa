package com.mosinsa.order.infra.feignclient.coupon;

import com.mosinsa.order.infra.feignclient.ResponseResult;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collection;
import java.util.Map;

@Component
@FeignClient(name = "coupon-service", url = "${feignclient.url.coupon}")
public interface CouponClient {

	@GetMapping("/coupons/{couponId}")
	ResponseResult<SimpleCouponResponse> getCoupon(@RequestHeader Map<String, Collection<String>> headers,
									  @PathVariable(value = "couponId") String couponId);

	@PostMapping("/coupons/{couponId}")
	ResponseResult<SimpleCouponResponse> useCoupon(@RequestHeader Map<String, Collection<String>> headers,
							 @PathVariable(value = "couponId") String couponId);

	@PostMapping("/coupons/{couponId}/cancel")
	ResponseResult<SimpleCouponResponse> cancelCoupon(@RequestHeader Map<String, Collection<String>> headers,
							 @PathVariable(value = "couponId") String couponId);

}
