package com.mosinsa.promotion.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PromotionTest {

    @Autowired
    PromotionRepository repository;

    @Test
    void equalsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        Promotion promotion = Promotion.create("title", "context", DateUnit.ONCE, new PromotionPeriod(now, now.plusDays(2)));
        Promotion save = repository.save(promotion);
        Promotion same = repository.findById(save.getId()).get();
        Promotion protectedConstructor = new Promotion();
        Promotion other = repository.findById(PromotionId.of("promotion1")).get();

        boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(save, same, protectedConstructor, other);
        assertThat(b).isTrue();
    }

}