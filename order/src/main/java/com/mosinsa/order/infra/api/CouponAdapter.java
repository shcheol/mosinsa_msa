package com.mosinsa.order.infra.api;

import com.mosinsa.order.infra.api.feignclient.coupon.CouponResponse;

import java.util.Collection;
import java.util.Map;

public interface CouponAdapter {
    ResponseResult<Void> useCoupon(Map<String, Collection<String>> headers, String couponId);

    ResponseResult<CouponResponse> getCoupon(Map<String, Collection<String>> headers, String couponId);
}
