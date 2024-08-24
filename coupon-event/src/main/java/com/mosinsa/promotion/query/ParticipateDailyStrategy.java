package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
class ParticipateDailyStrategy implements DateUnitStrategy {

    private final Clock clock;

    @Override
    public boolean isParticipated(List<PromotionHistory> historyList) {
        if (historyList.isEmpty()) {
            return false;
        }

        LocalDateTime lastParticipatedDateTime = historyList.get(0).getLastModifiedDate();
        LocalDate lastParticipatedDate = LocalDate.of(lastParticipatedDateTime.getYear(),
                lastParticipatedDateTime.getMonth(),
                lastParticipatedDateTime.getDayOfMonth());

        return !lastParticipatedDate.isBefore(LocalDate.now(clock));
    }

    @Override
    public boolean isSupport(DateUnit dateUnit) {
        return DateUnit.DAILY.equals(dateUnit);
    }
}