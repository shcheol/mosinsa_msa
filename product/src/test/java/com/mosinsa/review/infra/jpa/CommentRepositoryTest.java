package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:/db/test-init.sql")
class CommentRepositoryTest {

    @Autowired
	CommentRepository repository;

    @Test
    void findProductId() {
        String productId = repository.findProductId("commentId1").get();

        assertThat(productId).isEqualTo("productId1");
    }
}