package com.shopping.mosinsa.dto;

import com.shopping.mosinsa.entity.CouponEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CouponEventWrapper{

    private final CouponEvent couponEvent;
    private final String eventKey;

    public CouponEventWrapper(CouponEvent couponEvent) {
        this.couponEvent = couponEvent;
        this.eventKey = couponEvent.getEventName() + couponEvent.getId();
    }
}
