package com.mosinsa.promotion.query.conditionstrategy;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.command.domain.PromotionConditions;
import com.mosinsa.promotion.query.ConditionStrategy;
import org.springframework.stereotype.Component;

@Component
public class RandomCouponConditionStrategy implements ConditionStrategy {
    @Override
    public ConditionOption getConditionOption(String memberId) {
        return ConditionOption.EVERY_MEMBER;
    }

    @Override
    public boolean isSupport(PromotionConditions promotionConditions) {
        return PromotionConditions.RANDOM_COUPON.equals(promotionConditions);
    }
}
