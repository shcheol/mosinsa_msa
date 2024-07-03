package com.mosinsa.reaction.qeury.application;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReactionReaderTest {

	@Autowired
	ReactionReader reader;

	@Test
	void hasReacted() {
		ReactionSearchCondition condition1 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId1");
		boolean b = reader.hasReacted(condition1);
		assertThat(b).isFalse();

		ReactionSearchCondition condition2 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId2");
		boolean a = reader.hasReacted(condition2);
		assertThat(a).isTrue();
	}

	@Test
	void total() {
		ReactionSearchCondition condition1 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId2");
		assertThat(reader.total(condition1)).isEqualTo(1);


		ReactionSearchCondition condition2 = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1xx", ReactionType.LIKES, "");
		assertThat(reader.total(condition2)).isZero();
	}
}