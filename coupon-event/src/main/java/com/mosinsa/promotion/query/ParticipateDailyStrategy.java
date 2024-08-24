package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
class ParticipateDailyStrategy implements DateUnitStrategy {

    @Override
    public boolean isParticipated(List<PromotionHistory> historyList) {
        if (historyList.isEmpty()) {
            return false;
        }
        LocalDateTime lastParticipatedDateTime = historyList.get(0).getLastModifiedDate();
        LocalDate lastParticipatedDate = LocalDate.of(lastParticipatedDateTime.getYear(), lastParticipatedDateTime.getMonth(), lastParticipatedDateTime.getDayOfMonth());
        return !lastParticipatedDate.isBefore(LocalDate.now().minusDays(1));
    }

    @Override
    public boolean isSupport(DateUnit dateUnit) {
        return DateUnit.DAILY.equals(dateUnit);
    }
}