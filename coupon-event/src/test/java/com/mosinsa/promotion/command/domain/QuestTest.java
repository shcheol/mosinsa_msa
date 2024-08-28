package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class QuestTest extends InMemoryJpaTest {

    @Autowired
    QuestRepository repository;

    @Test
    void equalsAndHashcode() {
        Quest origin = repository.findById(1L).get();
        Quest same = repository.findById(1L).get();
        Quest protectedConstructor = new Quest();
        Quest other = repository.findById(2L).get();
        boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(origin, same, protectedConstructor, other);
        assertThat(b).isTrue();
    }

    @Test
    void value() {
        Quest quest = repository.findById(1L).get();
        assertThat(quest.getId()).isEqualTo(1L);
        PromotionId promotion1 = PromotionId.of("promotion1");
        assertThat(quest.getPromotion().getId()).isEqualTo(promotion1);
        assertThat(quest.getPromotionConditionOption()).isNotNull();
        assertThat(quest.getCouponGroupInfoList()).hasSize(1);
    }

}