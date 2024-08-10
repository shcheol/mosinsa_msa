package com.mosinsa.promotion.command.domain;

import com.mosinsa.common.event.Event;
import com.mosinsa.coupon.command.domain.CouponCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromotionCreatedEvent extends Event {

    private PromotionId promotionId;

    private int quantity;

    private CouponCondition details;
}
