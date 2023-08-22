package com.mosinsa.couponissue.dto;

import com.mosinsa.couponissue.entity.CouponEvent;
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
