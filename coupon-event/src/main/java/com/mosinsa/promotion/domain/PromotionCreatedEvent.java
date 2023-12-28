package com.mosinsa.promotion.domain;

import com.mosinsa.common.event.Event;
import com.mosinsa.coupon.domain.CouponDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromotionCreatedEvent extends Event {

    private PromotionId promotionId;

    private int quantity;

    private CouponDetails details;
}
