package com.mosinsa.review.command.application;

import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.infra.jpa.CommentRepository;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.review.ui.reqeust.DeleteCommentRequest;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteCommentRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	void reviewLikes() {
		CustomerInfo user1 = new CustomerInfo("writerId1", "writerName");
		CustomerInfo user2 = new CustomerInfo("writerId2", "writerName");

		String reviewId = "reviewId1";

		reviewService.likesReview(reviewId, user1);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getLikesCount())
				.isEqualTo(1);

		reviewService.likesReview(reviewId, user2);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getLikesCount())
				.isEqualTo(2);

		reviewService.likesReview(reviewId, user1);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getLikesCount())
				.isEqualTo(1);

		reviewService.likesReview(reviewId, user2);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getLikesCount())
				.isZero();
	}

	@Test
	void reviewLikesConcurrency() throws InterruptedException {

		String reviewId = "reviewId1";
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getLikesCount()).isZero();
		int size = 5;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					reviewService.likesReview(reviewId, new CustomerInfo(UUID.randomUUID().toString(), "name"));
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
				reviewRepository.findById(ReviewId.of(reviewId)).get().getLikesCount()
		).isEqualTo(size);
	}

	@Test
	void reviewDislikes() {
		CustomerInfo user1 = new CustomerInfo("writerId1", "writerName");
		CustomerInfo user2 = new CustomerInfo("writerId2", "writerName");

		String reviewId = "reviewId1";

		reviewService.dislikesReview(reviewId, user1);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getDislikesCount())
				.isEqualTo(1);

		reviewService.dislikesReview(reviewId, user2);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getDislikesCount())
				.isEqualTo(2);

		reviewService.dislikesReview(reviewId, user1);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getDislikesCount())
				.isEqualTo(1);

		reviewService.dislikesReview(reviewId, user2);
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getDislikesCount())
				.isZero();
	}

	@Test
	void reviewDislikesConcurrency() throws InterruptedException {

		String reviewId = "reviewId1";
		assertThat(reviewRepository.findById(ReviewId.of(reviewId)).get().getDislikesCount()).isZero();
		int size = 5;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					reviewService.dislikesReview(reviewId, new CustomerInfo(UUID.randomUUID().toString(), "name"));
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
				reviewRepository.findById(ReviewId.of(reviewId)).get().getDislikesCount()
		).isEqualTo(size);
	}

	@Test
	void commentLikes() {
		CustomerInfo user1 = new CustomerInfo("writerId1", "writerName");
		CustomerInfo user2 = new CustomerInfo("writerId2", "writerName");

		String reviewId = "reviewId1";
		String commentId = "commentId1";

		reviewService.likesComment(reviewId, commentId, user1);
		assertThat(commentRepository.findById(commentId).get().getLikesCount())
				.isEqualTo(1);

		reviewService.likesComment(reviewId,commentId, user2);
		assertThat(commentRepository.findById(commentId).get().getLikesCount())
				.isEqualTo(2);

		reviewService.likesComment(reviewId, commentId,user1);
		assertThat(commentRepository.findById(commentId).get().getLikesCount())
				.isEqualTo(1);

		reviewService.likesComment(reviewId,commentId, user2);
		assertThat(commentRepository.findById(commentId).get().getLikesCount())
				.isZero();
	}

	@Test
	void commentLikesConcurrency() throws InterruptedException {

		String reviewId = "reviewId1";
		String commentId = "commentId1";
		assertThat(commentRepository.findById(commentId).get().getLikesCount()).isZero();
		int size = 5;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					reviewService.likesComment(reviewId, commentId, new CustomerInfo(UUID.randomUUID().toString(), "name"));
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
				commentRepository.findById(commentId).get().getLikesCount()
		).isEqualTo(size);
	}

	@Test
	void commentDislikes() {
		CustomerInfo user1 = new CustomerInfo("writerId1", "writerName");
		CustomerInfo user2 = new CustomerInfo("writerId2", "writerName");

		String reviewId = "reviewId1";
		String commentId = "commentId1";

		reviewService.dislikesComment(reviewId, commentId, user1);
		assertThat(commentRepository.findById(commentId).get().getDislikesCount())
				.isEqualTo(1);

		reviewService.dislikesComment(reviewId,commentId, user2);
		assertThat(commentRepository.findById(commentId).get().getDislikesCount())
				.isEqualTo(2);

		reviewService.dislikesComment(reviewId, commentId,user1);
		assertThat(commentRepository.findById(commentId).get().getDislikesCount())
				.isEqualTo(1);

		reviewService.dislikesComment(reviewId,commentId, user2);
		assertThat(commentRepository.findById(commentId).get().getDislikesCount())
				.isZero();
	}

	@Test
	void commentDislikesConcurrency() throws InterruptedException {

		String reviewId = "reviewId1";
		String commentId = "commentId1";
		assertThat(commentRepository.findById(commentId).get().getDislikesCount()).isZero();
		int size = 5;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					reviewService.dislikesComment(reviewId, commentId, new CustomerInfo(UUID.randomUUID().toString(), "name"));
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
				commentRepository.findById(commentId).get().getDislikesCount()
		).isEqualTo(size);
	}
}