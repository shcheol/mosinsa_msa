package com.mosinsa.order.application;

import com.mosinsa.order.infra.feignclient.CouponClient;
import com.mosinsa.order.infra.feignclient.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

	private final CouponClient couponClient;

	public CouponResponse useCoupon(Map<String, Collection<String>> headers, String couponId){
		return couponClient.useCoupon(headers, couponId);
	}
}
