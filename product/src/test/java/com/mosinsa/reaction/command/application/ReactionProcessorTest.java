package com.mosinsa.reaction.command.application;

import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.command.domain.ReactionId;
import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.jpa.ReactionRepository;
import com.mosinsa.reaction.qeury.application.ReactionReader;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReactionProcessorTest {

	@Autowired
	ReactionProcessor service;

	@Autowired
	ReactionReader reader;

	@Autowired
	ReactionRepository repository;

	@Test
	void reaction() {
		ReactionSearchCondition condition = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId1");

		String id = service.reaction(condition);

		Reaction reaction = repository.findById(ReactionId.of(id)).get();
		assertThat(reaction.getReactionType()).isEqualTo(ReactionType.LIKES);
		assertThat(reaction.getTargetType()).isEqualTo(TargetEntity.PRODUCT);
		assertThat(reaction.getTargetId()).isEqualTo("productId1");
		assertThat(reaction.isActive()).isTrue();
	}
	@Test
	void reactionCancel() {
		ReactionSearchCondition condition = new ReactionSearchCondition(TargetEntity.PRODUCT, "productId1", ReactionType.LIKES, "memberId2");

		String id = service.cancel(condition);

		Reaction reaction = repository.findById(ReactionId.of(id)).get();
		assertThat(reaction.getReactionType()).isEqualTo(ReactionType.LIKES);
		assertThat(reaction.getTargetType()).isEqualTo(TargetEntity.PRODUCT);
		assertThat(reaction.getTargetId()).isEqualTo("productId1");
		assertThat(reaction.isActive()).isFalse();
	}

	@Test
	void likesConcurrency() throws InterruptedException {

		String productId = "xxxx";
		ReactionSearchCondition reactionSearchCondition = new ReactionSearchCondition(TargetEntity.PRODUCT, productId, ReactionType.LIKES, "");
		assertThat(
				reader.total(reactionSearchCondition)
		).isZero();
		int size = 10;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					service.reaction(new ReactionSearchCondition(TargetEntity.PRODUCT, productId, ReactionType.LIKES, UUID.randomUUID().toString()));
				}finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		long end = System.currentTimeMillis();
		System.out.println("실행 시간: " + (end - start));

		es.shutdown();
		assertThat(
				reader.total(reactionSearchCondition)
		).isEqualTo(size);

	}


}