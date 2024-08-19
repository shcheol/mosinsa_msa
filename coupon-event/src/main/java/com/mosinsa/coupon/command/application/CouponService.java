package com.mosinsa.coupon.command.application;

import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.command.domain.CouponIssuedEvent;
import com.mosinsa.coupon.infra.kafka.ParticipatedEvent;

public interface CouponService {

    CouponId issue(CouponIssuedEvent event);
    CouponId issue(String memberId, long couponGroupSequence);

    void useCoupon(String couponId);

    void rollbackCoupon(String couponId);
}
