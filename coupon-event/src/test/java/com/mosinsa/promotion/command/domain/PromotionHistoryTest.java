package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.promotion.infra.jpa.QuestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionHistoryTest extends InMemoryJpaTest {

	@Autowired
	PromotionHistoryRepository repository;

	@Autowired
	QuestRepository questRepository;

	@Test
	void save(){
		Quest quest = questRepository.findById(1L).get();
		PromotionHistory history = repository.save(PromotionHistory.of("memberId3", quest));

		assertThat(history.getQuest()).isEqualTo(quest);
		assertThat(history.getMemberId()).isEqualTo("memberId3");
	}

	@Test
	void equalsAndHashcode(){
		PromotionHistory origin = repository.findById(1L).get();
		PromotionHistory same = repository.findById(1L).get();
		PromotionHistory protectedConstructor = new PromotionHistory();
		PromotionHistory other = repository.findById(2L).get();
		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(origin, same, protectedConstructor, other);
		assertThat(b).isTrue();
	}

}