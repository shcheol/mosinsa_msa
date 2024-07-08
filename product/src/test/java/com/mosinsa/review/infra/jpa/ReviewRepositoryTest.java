package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.netflix.discovery.converters.Auto;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReviewRepositoryTest {

	@Autowired
	ReviewRepository repository;

	@Test
	@Transactional
	void lazyLoading(){
		Review reviewId1 = repository.findById(ReviewId.of("reviewId1")).get();
		List<Comment> comments = reviewId1.getComments();
		for (Comment comment : comments) {
			System.out.println(comment);
		}
	}

	@Test
	void joinFetch(){
		Review review = repository.findReviewByIdAndCommentsJoinFetch(ReviewId.of("reviewId1"));
		List<Comment> comments = review.getComments();
		for (Comment comment : comments) {
			System.out.println(comment);
		}
	}

	@Test
	void join(){
		List<Tuple> review = repository.findReviewByIdAndComments(ReviewId.of("reviewId1"));

		System.out.println(review.size());

	}

}