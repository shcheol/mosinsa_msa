package com.mosinsa.coupon.command.domain;

import lombok.Getter;

@Getter
public enum CouponState {

    CREATED, ISSUED, USED, EXPIRED,
}
