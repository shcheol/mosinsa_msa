package com.mosinsa.reaction.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.code.TestClass;
import com.mosinsa.reaction.infra.jpa.ReactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReactionTest {

	@Autowired
	ReactionRepository repository;

	@Test
	void equalsAndHashCode() {
		Reaction reactionA = Reaction.of(TargetEntity.PRODUCT, "id", ReactionType.LIKES, "member");
		Reaction save = repository.save(reactionA);
		Reaction reactionB = repository.findById(save.getId()).get();

		Reaction reactionC = Reaction.of(TargetEntity.PRODUCT, "id3", ReactionType.LIKES, "member");

		Reaction protectedConstructor = new Reaction();

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(reactionA, reactionB, protectedConstructor, reactionC);
		assertThat(b).isTrue();
	}

}