package com.mosinsa.coupon.command.domain;

import com.mosinsa.common.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CouponIssuedEvent extends Event {
    private final String memberId;
    private final Long minUsePrice;
    private final LocalDate duringDate;
    private final DiscountPolicy discountPolicy;
}
