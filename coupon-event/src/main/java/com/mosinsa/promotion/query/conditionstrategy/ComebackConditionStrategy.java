package com.mosinsa.promotion.query.conditionstrategy;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.command.domain.PromotionConditions;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import com.mosinsa.promotion.query.ConditionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComebackConditionStrategy implements ConditionStrategy {

    private final OrderAdapter orderAdapter;

    @Override
    public ConditionOption getConditionOption(String memberId) {
        if (orderAdapter.getMyOrders(memberId).orElseThrow().isEmpty()) {
            return ConditionOption.NEW_MEMBER;
        }

        return ConditionOption.OLD_MEMBER;
    }

    @Override
    public boolean isSupport(PromotionConditions promotionConditions) {
        return PromotionConditions.COMEBACK.equals(promotionConditions);
    }
}
