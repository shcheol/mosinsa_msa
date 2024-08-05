package com.mosinsa.coupon.command.domain;

import com.mosinsa.common.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponIssuedEvent extends Event {
    private final String memberId;
    private final String promotionId;
}
