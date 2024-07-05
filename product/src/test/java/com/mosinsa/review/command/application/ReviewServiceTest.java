package com.mosinsa.review.command.application;

import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.infra.jpa.CommentRepository;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.review.ui.reqeust.DeleteCommentRequest;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteCommentRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ReviewServiceTest {

	@Autowired
	ReviewService reviewService;
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	CommentRepository commentRepository;

	@Test
	void writeReview() {
		WriteReviewRequest writeReviewRequest = new WriteReviewRequest("writerId1", "writer", "productId1", "test");

		String saveId = reviewService.writeReview(writeReviewRequest);

		Review findReview = reviewRepository.findById(ReviewId.of(saveId)).get();
		assertThat(findReview.getContents()).isEqualTo("test");
		assertThat(findReview.getWriter().getWriterId()).isEqualTo("writerId1");
		assertThat(findReview.getWriter().getName()).isEqualTo("writer");
		assertThat(findReview.getProductId()).isEqualTo("productId1");
		assertThat(findReview.isDeleted()).isFalse();
	}

	@Test
	void delete() {
		String id = "reviewId1";
		Review before = reviewRepository.findById(ReviewId.of(id)).get();
		assertThat(before.isDeleted()).isFalse();

		DeleteReviewRequest request = new DeleteReviewRequest("writerId1");
		reviewService.deleteReview(id, request);

		Review after = reviewRepository.findById(ReviewId.of(id)).get();
		assertThat(after.isDeleted()).isTrue();
	}


	@Test
	void deleteFail() {
		String id = "reviewId1";
		Review before = reviewRepository.findById(ReviewId.of(id)).get();
		assertThat(before.isDeleted()).isFalse();

		DeleteReviewRequest request = new DeleteReviewRequest("writerId2");
		assertThrows(IllegalStateException.class, () -> reviewService.deleteReview(id, request));
	}

	@Test
	void writeComment() {

		WriteCommentRequest writeReviewRequest = new WriteCommentRequest("writerId1", "writer", "test");

		String saveId = reviewService.writeComment("reviewId1", writeReviewRequest);

		Comment findComment = commentRepository.findById(saveId).get();
		assertThat(findComment.getContents()).isEqualTo("test");
		assertThat(findComment.getWriter().getWriterId()).isEqualTo("writerId1");
		assertThat(findComment.getWriter().getName()).isEqualTo("writer");
		assertThat(findComment.isDeleted()).isFalse();
	}

	@Test
	void deleteComment() {
		String id = "commentId1";
		Comment before = commentRepository.findById(id).get();
		assertThat(before.isDeleted()).isFalse();

		DeleteCommentRequest request = new DeleteCommentRequest("writerId3");
		reviewService.deleteComment("reviewId1", id, request);

		Comment after = commentRepository.findById(id).get();
		assertThat(after.isDeleted()).isTrue();
	}

	@Test
	void deleteCommentFail() {
		String id = "commentId1";
		Comment before = commentRepository.findById(id).get();
		assertThat(before.isDeleted()).isFalse();

		DeleteCommentRequest request = new DeleteCommentRequest("writerId1");
		assertThrows(ReviewException.class, () -> reviewService.deleteComment("reviewId", id, request));
	}
}