package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParticipateOnceStrategy implements DateUnitStrategy{
    @Override
    public boolean isParticipated(List<PromotionHistory> historyList) {
        return !historyList.isEmpty();
    }

    @Override
    public boolean isSupport(DateUnit dateUnit) {
        return DateUnit.ONCE.equals(dateUnit);
    }
}
