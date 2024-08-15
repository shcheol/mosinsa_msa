package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.command.domain.PromotionConditions;
import com.mosinsa.promotion.infra.api.OrderAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewMemberConditionStrategy implements ConditionStrategy {

    private final OrderAdapter orderAdapter;

    @Override
    public ConditionOption getConditionOption(String memberId) {

        return ConditionOption.NEW_MEMBER;
    }

    @Override
    public boolean isSupport(PromotionConditions promotionConditions) {
        return PromotionConditions.NEW_MEMBER.equals(promotionConditions);
    }
}
