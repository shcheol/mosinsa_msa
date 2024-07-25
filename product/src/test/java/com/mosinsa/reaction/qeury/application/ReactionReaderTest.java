package com.mosinsa.reaction.qeury.application;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReactionReaderTest {

	@Autowired
    ReactionReaderImpl reader;

	@Test
	void hasReacted() {
		ReactionSearchCondition condition1 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId1");
		assertThat(reader.hasReacted(condition1)).isFalse();

		ReactionSearchCondition condition2 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId2");
		assertThat(reader.hasReacted(condition2)).isTrue();

		ReactionSearchCondition condition3 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId11234132423", ReactionType.LIKES, "memberId2");
		assertThat(reader.hasReacted(condition3)).isFalse();
	}

	@Test
	void total() {
		ReactionSearchCondition condition2 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1xx", ReactionType.LIKES, "");
		assertThat(reader.total(condition2)).isZero();
	}
}