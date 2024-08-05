package com.mosinsa.coupon.command.application;

import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.command.domain.CouponIssuedEvent;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;

public interface CouponService {

    void createAll(PromotionCreatedEvent event);

    void createAllByBatchInsert(PromotionCreatedEvent event);

    void createForNewMember(String memberId);

    CouponId issue(CouponIssuedEvent event);

    void useCoupon(String couponId);

    void rollbackCoupon(String couponId);
}
