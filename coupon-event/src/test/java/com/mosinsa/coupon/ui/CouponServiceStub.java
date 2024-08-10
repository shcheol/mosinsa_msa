package com.mosinsa.coupon.ui;

import com.mosinsa.coupon.command.application.CouponService;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.command.domain.CouponIssuedEvent;

public class CouponServiceStub implements CouponService {

    @Override
    public CouponId issue(CouponIssuedEvent event) {
        return CouponId.of("couponId");
    }

    @Override
    public void useCoupon(String couponId) {

    }

    @Override
    public void rollbackCoupon(String couponId) {

    }
}
