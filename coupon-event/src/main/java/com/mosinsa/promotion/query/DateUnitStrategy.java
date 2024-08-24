package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;

import java.util.List;

public interface DateUnitStrategy {

    boolean isParticipated(List<PromotionHistory> historyList);

    boolean isSupport(DateUnit dateUnit);
}
