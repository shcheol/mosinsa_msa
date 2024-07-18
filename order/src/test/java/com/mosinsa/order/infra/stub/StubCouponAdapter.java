package com.mosinsa.order.infra.stub;

import com.mosinsa.order.command.domain.DiscountPolicy;
import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.infra.api.feignclient.coupon.CouponResponse;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public class StubCouponAdapter implements CouponAdapter {
    @Override
    public ResponseResult<Void> useCoupon(Map<String, Collection<String>> headers, String couponId) {
        System.out.println("");
        return null;
    }

    @Override
    public ResponseResult<CouponResponse> getCoupon(Map<String, Collection<String>> headers, String couponId) {
        System.out.println("StubCouponAdapter.getCoupon");
        return ResponseResult.execute(() -> new CouponResponse("couponId", DiscountPolicy.TEN_PERCENTAGE,
                null,null,null,null,true));
    }
}
