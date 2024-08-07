package com.mosinsa.reaction.command.application;

import com.mosinsa.reaction.command.domain.ReactionType;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.reaction.query.application.ReactionReaderImpl;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
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
class ReactionServiceTest {

    @Autowired
	ReactionServiceImpl service;
    @Autowired
    ReactionReaderImpl reader;

    @Test
    void likesConcurrency() throws InterruptedException {

        String productId = "xxxx";
        ReactionSearchCondition reactionSearchCondition = new ReactionSearchCondition(TargetEntity.PRODUCT, productId, ReactionType.LIKES, "memberId");
        assertThat(reader.total(reactionSearchCondition)).isZero();

        int size = 5;
        ExecutorService es = Executors.newFixedThreadPool(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            es.execute(() -> {
                try {
                    service.reaction(new ReactionSearchCondition(TargetEntity.PRODUCT, productId, ReactionType.LIKES, UUID.randomUUID().toString()));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("실행 시간: " + (end - start));

        es.shutdown();
        assertThat(reader.total(reactionSearchCondition)).isEqualTo(size);
    }

	@Test
	void cancelConcurrency() throws InterruptedException {

		String productId = "xxxx";
		ReactionSearchCondition reactionSearchCondition = new ReactionSearchCondition(TargetEntity.PRODUCT, productId, ReactionType.LIKES, "");
		assertThat(reader.total(reactionSearchCondition)).isZero();

		int size = 5;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					ReactionSearchCondition condition = new ReactionSearchCondition(TargetEntity.PRODUCT, productId, ReactionType.LIKES, UUID.randomUUID().toString());
					service.reaction(condition);
					service.cancel(condition);
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		long end = System.currentTimeMillis();
		System.out.println("실행 시간: " + (end - start));

		es.shutdown();
		assertThat(reader.total(reactionSearchCondition)).isZero();
	}

}