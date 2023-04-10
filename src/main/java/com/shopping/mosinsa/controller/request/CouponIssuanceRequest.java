package com.shopping.mosinsa.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponIssuanceRequest {

    private Long customerId;
    private Long couponEventId;

    public CouponIssuanceRequest(Long customerId, Long couponEventId) {
        this.customerId = customerId;
        this.couponEventId = couponEventId;
    }
}
