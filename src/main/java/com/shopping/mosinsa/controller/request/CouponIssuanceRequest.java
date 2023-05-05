package com.shopping.mosinsa.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CouponIssuanceRequest {

    private String eventName;
    private Long couponEventId;
    private Long customerId;
    public CouponIssuanceRequest(String eventName, Long couponEventId, Long customerId) {
        this.eventName = eventName;
        this.couponEventId = couponEventId;
        this.customerId = customerId;

    }
}
