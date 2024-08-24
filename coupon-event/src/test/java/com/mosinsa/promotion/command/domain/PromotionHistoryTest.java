package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.promotion.infra.jpa.QuestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionHistoryTest extends InMemoryJpaTest {

    @Autowired
    PromotionHistoryRepository repository;

    @Autowired
    QuestRepository questRepository;

    @Test
    void save() {
        Quest quest = questRepository.findById(1L).get();
        PromotionHistory history = repository.save(PromotionHistory.of("memberId3", quest));

        assertThat(history.getQuest()).isEqualTo(quest);
        assertThat(history.getMemberId()).isEqualTo("memberId3");
    }

    @Test
    void equalsAndHashcode() {

        PromotionHistory noId = PromotionHistory.of("", null);
        PromotionHistory noId2 = PromotionHistory.of("3", null);

        PromotionHistory origin = repository.findById(1L).get();
        PromotionHistory same = repository.findById(1L).get();
        PromotionHistory protectedConstructor = new PromotionHistory();
        PromotionHistory other = repository.findById(2L).get();

        Assertions.assertThat(noId).isEqualTo(noId2).isNotEqualTo(origin);

        boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(origin, same, protectedConstructor, other);
        assertThat(b).isTrue();
    }

}