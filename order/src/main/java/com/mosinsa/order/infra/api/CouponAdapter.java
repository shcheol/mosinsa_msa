package com.mosinsa.order.infra.api;

import com.mosinsa.order.infra.api.feignclient.coupon.CouponResponse;

public interface CouponAdapter {
    ResponseResult<Void> useCoupon(String couponId);

    ResponseResult<CouponResponse> getCoupon(String couponId);
}
