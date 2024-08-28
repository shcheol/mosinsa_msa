package com.mosinsa.review.command.application;

import com.mosinsa.code.TestClass;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.command.domain.CommentRepository;
import com.mosinsa.review.command.domain.ReviewRepository;
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
	ReviewServiceImpl reviewService;
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
		assertThat(after.isDeleted()).isTrue();
	}

	@Test
	void deleteCommentFail() {
		String id = "commentId1";
		Comment before = commentRepository.findById(id).get();
		assertThat(before.isDeleted()).isFalse();

		DeleteCommentRequest request = new DeleteCommentRequest("writerId1xxx");
		assertThrows(ReviewException.class, () -> reviewService.deleteComment("reviewIdxxxx", id, request));

		assertThrows(ReviewException.class, () -> reviewService.deleteComment("reviewId1", "commentId1xxx", request));

		assertThrows(IllegalStateException.class, () -> reviewService.deleteComment("reviewId1", "commentId1", request));

	}

	@Test
	void equalsAndHashcode(){
		String id = "reviewId1";
		Review a = reviewRepository.findById(ReviewId.of(id)).get();
		Review b = reviewRepository.findById(ReviewId.of(id)).get();

		assertThat(a).isEqualTo(a).isEqualTo(b).hasSameHashCodeAs(b)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		Review c = reviewRepository.findById(ReviewId.of("reviewId2")).get();
		assertThat(a).isNotEqualTo(c).doesNotHaveSameHashCodeAs(c);


		Comment d = commentRepository.findById("commentId1").get();
		Comment e = commentRepository.findById("commentId1").get();
		assertThat(d).isEqualTo(d).isEqualTo(e).hasSameHashCodeAs(e)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		Comment f = commentRepository.findById("commentId2").get();
		assertThat(d).isNotEqualTo(f).doesNotHaveSameHashCodeAs(f);
	}
}