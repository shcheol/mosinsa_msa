package com.mosinsa.coupon.domain;

import lombok.Getter;

@Getter
public enum CouponState {

    CREATED, ISSUED, USED, EXPIRED,
}
