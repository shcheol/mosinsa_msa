package com.mosinsa.order.infra.feignclient.coupon;

public record SimpleCouponResponse(String couponId, String discountPolicy, boolean available) {

    public static SimpleCouponResponse empty() {
        return new SimpleCouponResponse("","",false);
    }
    public static SimpleCouponResponse of(CouponResponse response) {
        if (response == null){
            return SimpleCouponResponse.empty();
        }
        return new SimpleCouponResponse(response.couponId(), response.discountPolicy(), response.available());
    }
}
