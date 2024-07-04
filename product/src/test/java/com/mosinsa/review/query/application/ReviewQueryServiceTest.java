package com.mosinsa.review.query.application;

import com.mosinsa.review.query.application.dto.CommentSummaryDto;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.mosinsa.review.ui.reqeust.ReviewSummaryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReviewQueryServiceTest {

	@Autowired
	ReviewQueryService queryService;

	@Test
	void findProductReviews() {
		Page<ReviewSummaryDto> reviews = queryService.findProductReviews(new ReviewSummaryRequest("productId1"), PageRequest.of(0, 10));

		List<String> ids = reviews.getContent().stream().map(ReviewSummaryDto::getReviewId).toList();
		assertThat(ids).containsExactly("reviewId1");
	}

	@Test
	void findReviewComments() {
		Page<CommentSummaryDto> comments = queryService.findReviewComments("reviewId1", PageRequest.of(0, 10));
		List<String> ids = comments.getContent().stream().map(CommentSummaryDto::getCommentId).toList();
		assertThat(ids).containsExactly("commentId1","commentId2","commentId3","commentId4");
	}
}