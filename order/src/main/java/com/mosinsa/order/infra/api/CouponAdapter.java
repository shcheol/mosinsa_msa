package com.mosinsa.order.infra.api;

public interface CouponAdapter {
    ResponseResult<Void> useCoupon(String couponId);

}
