package com.mosinsa.reaction.infra.jpa;

import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReactionRepositoryTest {

	@Autowired ReactionRepository repository;

	@Test
	void auditingEntity(){
		Reaction save = repository.save(Reaction.of(TargetEntity.PRODUCT, "id", ReactionType.LIKES, "id"));
		assertThat(save.getCreatedDate()).isBefore(LocalDateTime.now());
		assertThat(save.getLastModifiedDate()).isBefore(LocalDateTime.now());
	}

}