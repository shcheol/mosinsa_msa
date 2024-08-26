package com.mosinsa.order.infra.stub;

import com.mosinsa.order.command.domain.DiscountPolicy;
import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.infra.api.feignclient.coupon.CouponResponse;

public class StubCouponAdapter implements CouponAdapter {
    @Override
    public ResponseResult<Void> useCoupon(String couponId) {
        System.out.println("use");
        return ResponseResult.execute(()-> System.out.println(couponId));
    }

    @Override
    public ResponseResult<CouponResponse> getCoupon(String couponId) {
        System.out.println("StubCouponAdapter.getCoupon");
        return ResponseResult.execute(() -> new CouponResponse("couponId", DiscountPolicy.TEN_PERCENTAGE,
                null, null, null, null, true));
    }
}
