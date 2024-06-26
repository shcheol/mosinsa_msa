package com.mosinsa.review.command.application;

import com.mosinsa.common.aop.RedissonLock;
import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.command.domain.Writer;
import com.mosinsa.review.infra.jpa.CommentRepository;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.review.infra.kafka.*;
import com.mosinsa.review.ui.argumentresolver.CustomerInfo;
import com.mosinsa.review.ui.reqeust.DeleteCommentRequest;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteCommentRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final CommentRepository commentRepository;
	private final CommentLikesService commentLikesService;
	private final ReviewLikesService reviewLikesService;

	private static final String REVIEW_LIKES_LOCK_KEY = "review_likes_lock_key";
	private static final String COMMENT_LIKES_LOCK_KEY = "comment_likes_lock_key";

	@Transactional
	public String writeReview(WriteReviewRequest request) {

		Writer writer = Writer.of(request.writerId(), request.writerName());
		Review review = Review.of(writer, request.productId(), request.content());
		Review save = reviewRepository.save(review);
		return save.getReviewId().getId();
	}

	@Transactional
	public void deleteReview(String reviewId, DeleteReviewRequest request) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		review.delete(request.writerId());
	}

	@Transactional
	@RedissonLock(value = REVIEW_LIKES_LOCK_KEY)
	public void likesReview(String reviewId, CustomerInfo customerInfo) {
		try {
			Review review = reviewLikesService.likesReview(reviewId, customerInfo.id());
			KafkaEvents.raise("review-likes-topic", new ReviewLikesEvent(review.getProductId(), reviewId, false));
		} catch (DataIntegrityViolationException e) {
			Review review = reviewLikesService.likesReviewCancel(reviewId, customerInfo.id());
			KafkaEvents.raise("review-likes-topic", new ReviewLikesEvent(review.getProductId(), reviewId, true));
		}
	}

	@Transactional
	@RedissonLock(value = REVIEW_LIKES_LOCK_KEY)
	public void dislikesReview(String reviewId, CustomerInfo customerInfo) {
		try {
			Review review = reviewLikesService.dislikesReview(reviewId, customerInfo.id());
			KafkaEvents.raise("review-dislikes-topic", new ReviewDislikesEvent(review.getProductId(), reviewId, false));
		} catch (DataIntegrityViolationException e) {
			Review review = reviewLikesService.dislikesReviewCancel(reviewId, customerInfo.id());
			KafkaEvents.raise("review-dislikes-topic", new ReviewDislikesEvent(review.getProductId(), reviewId, true));
		}
	}

	@Transactional
	public String writeComment(String reviewId, WriteCommentRequest request) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		Writer writer = Writer.of(request.writerId(), request.writerName());
		Comment comment = Comment.of(writer, review, request.content());
		review.writeComment(comment);

		return comment.getId();
	}

	@Transactional
	public void deleteComment(String reviewId, String commentId, DeleteCommentRequest request) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		review.deleteComment();
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_COMMENT));
		comment.delete(request.writerId());
	}

	@Transactional
	@RedissonLock(value = COMMENT_LIKES_LOCK_KEY)
	public void likesComment(String reviewId, String commentId, CustomerInfo customerInfo) {

		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		try {
			commentLikesService.likesComment(commentId, customerInfo.id());
			KafkaEvents.raise("comment-likes-topic", new CommentLikesEvent(review.getProductId(), reviewId, commentId, false));
		} catch (DataIntegrityViolationException e) {
			commentLikesService.likesCommentCancel(commentId, customerInfo.id());
			KafkaEvents.raise("comment-likes-topic", new CommentLikesEvent(review.getProductId(), reviewId, commentId, true));
		}
	}

	@Transactional
	@RedissonLock(value = COMMENT_LIKES_LOCK_KEY)
	public void dislikesComment(String reviewId, String commentId, CustomerInfo customerInfo) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		try {
			commentLikesService.dislikesComment(commentId, customerInfo.id());
			KafkaEvents.raise("comment-dislikes-topic", new CommentDislikesEvent(review.getProductId(), reviewId, commentId, false));
		} catch (DataIntegrityViolationException e) {
			commentLikesService.dislikesCommentCancel(commentId, customerInfo.id());
			KafkaEvents.raise("comment-dislikes-topic", new CommentDislikesEvent(review.getProductId(), reviewId, commentId, true));
		}
	}

}
