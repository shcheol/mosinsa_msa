package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.PromotionConditions;
import com.mosinsa.promotion.query.ConditionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConditionOptionFinder {

    private final List<ConditionStrategy> conditionStrategies;

    public ConditionStrategy findConditionOption(PromotionConditions promotionConditions) {
        return conditionStrategies.stream()
                .filter(conditionStrategy -> conditionStrategy.isSupport(promotionConditions))
                .findAny()
                .orElseThrow();
    }
}
