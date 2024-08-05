package com.mosinsa.coupon.ui;

import com.mosinsa.coupon.command.application.CouponService;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.command.domain.CouponIssuedEvent;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;

public class CouponServiceStub implements CouponService {
    @Override
    public void createAll(PromotionCreatedEvent event) {

    }

    @Override
    public void createAllByBatchInsert(PromotionCreatedEvent event) {

    }

    @Override
    public void createForNewMember(String memberId) {

    }

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
