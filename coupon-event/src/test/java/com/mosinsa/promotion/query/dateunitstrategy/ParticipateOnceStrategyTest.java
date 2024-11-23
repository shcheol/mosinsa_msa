package com.mosinsa.promotion.query.dateunitstrategy;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;
import com.mosinsa.promotion.query.dateunitstrategy.ParticipateOnceStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ParticipateOnceStrategyTest {
    @Test
    void isParticipated() {
        ParticipateOnceStrategy participateOnceStrategy = new ParticipateOnceStrategy();


        Assertions.assertThat(participateOnceStrategy.isParticipated(List.of())).isFalse();
        Assertions.assertThat(participateOnceStrategy.isParticipated(List.of(PromotionHistory.of("",null)))).isTrue();
    }

    @Test
    void isSupport() {
        ParticipateOnceStrategy participateOnceStrategy = new ParticipateOnceStrategy();
        Assertions.assertThat(participateOnceStrategy.isSupport(DateUnit.DAILY)).isFalse();
        Assertions.assertThat(participateOnceStrategy.isSupport(DateUnit.ONCE)).isTrue();
    }
}