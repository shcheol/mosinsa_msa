package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.command.domain.PromotionConditions;

public interface ConditionStrategy {

    ConditionOption getConditionOption(String memberId);

    boolean isSupport(PromotionConditions promotionConditions);
}
