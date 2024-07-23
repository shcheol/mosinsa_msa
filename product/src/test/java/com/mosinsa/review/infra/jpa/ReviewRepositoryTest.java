package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.querydsl.core.Tuple;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReviewRepositoryTest {

	@Autowired
	ReviewRepository repository;

	@Test
	void throwLazyInitializationException() {
		Review reviewId1 = repository.findById(ReviewId.of("reviewId1")).get();
		List<Comment> comments = reviewId1.getComments();

		assertThrows(LazyInitializationException.class, () -> comments.size());
	}

	@Test
	void joinFetch() {
		Review review = repository.findReviewByIdAndCommentsJoinFetch(ReviewId.of("reviewId1"));
		assertThat(review.getComments().size()).isEqualTo(4);
	}

	@Test
	void join() {
		List<Tuple> review = repository.findReviewByIdAndComments(ReviewId.of("reviewId1"));

		assertThat(review.size()).isEqualTo(4);

	}

}