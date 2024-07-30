package com.mosinsa.coupon.query.application.dto;

import com.mosinsa.coupon.command.domain.Coupon;
import com.mosinsa.coupon.command.domain.CouponCondition;
import com.mosinsa.coupon.command.domain.CouponState;

import java.time.LocalDateTime;

public record CouponDetails(
        String couponId,
        LocalDateTime issuedDate,
        String memberId,
        CouponState state,
        CouponCondition details) {
    public static CouponDetails convert(Coupon coupon) {
        return new CouponDetails(
                coupon.getId().getId(),
                coupon.getIssuedDate(),
                coupon.getMemberId(),
                coupon.getState(),
                coupon.getCondition()
        );
    }
}
