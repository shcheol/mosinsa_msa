package com.shopping.mosinsa.dto;

import com.shopping.mosinsa.entity.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CouponDto {
    private final Long id;

    private final Long customerId;

    private final Long couponEventId;

    private final String couponEventName;

    private final DiscountPolicy discountPolicy;

    private final LocalDateTime expirationPeriod;

    private final CouponStatus status;

    public CouponDto(Coupon coupon) {
        this.id = coupon.getId();
        this.customerId = coupon.getCustomer().getId();
        this.couponEventId = coupon.getCouponEvent().getId();
        this.couponEventName = coupon.getCouponEvent().getEventName();
        this.discountPolicy = coupon.getDiscountPolicy();
        this.expirationPeriod = coupon.getExpirationPeriod();
        this.status = coupon.getStatus();
    }
}
