package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;
import com.mosinsa.promotion.command.domain.PromotionHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParticipateDailyStrategyTest {

    @Autowired
    PromotionHistoryRepository repository;

    @Test
    void isParticipated() {
        PromotionHistory save = repository.save(PromotionHistory.of("1", null));
        Clock fixed = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        ParticipateDailyStrategy participateDailyStrategy = new ParticipateDailyStrategy(fixed);
        Assertions.assertThat(participateDailyStrategy.isParticipated(List.of())).isFalse();
        Assertions.assertThat(participateDailyStrategy.isParticipated(List.of(save))).isTrue();

        Clock fixed1 = Clock.fixed(Instant.now().plus(1, ChronoUnit.DAYS), ZoneId.systemDefault());
        ParticipateDailyStrategy participateDailyStrategy2 = new ParticipateDailyStrategy(fixed1);
        Assertions.assertThat(participateDailyStrategy2.isParticipated(List.of(save))).isFalse();
    }

    @Test
    void isSupport() {
        Clock fixed = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        ParticipateDailyStrategy participateDailyStrategy = new ParticipateDailyStrategy(fixed);
        Assertions.assertThat(participateDailyStrategy.isSupport(DateUnit.DAILY)).isTrue();
        Assertions.assertThat(participateDailyStrategy.isSupport(DateUnit.ONCE)).isFalse();
    }

}