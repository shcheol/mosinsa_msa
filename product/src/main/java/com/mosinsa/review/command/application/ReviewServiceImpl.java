package com.mosinsa.review.command.application;

import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.command.domain.Writer;
import com.mosinsa.review.infra.jpa.CommentRepository;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.review.ui.reqeust.DeleteCommentRequest;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteCommentRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final CommentRepository commentRepository;


	@Override
	@Transactional
	public String writeReview(WriteReviewRequest request) {

		Writer writer = Writer.of(request.writerId(), request.writerName());
		Review review = Review.of(writer, request.productId(), request.content());
		Review save = reviewRepository.save(review);
		return save.getReviewId().getId();
	}

	@Override
	@Transactional
	public void deleteReview(String reviewId, DeleteReviewRequest request) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		review.delete(request.writerId());
	}

	@Override
	@Transactional
	public String writeComment(String reviewId, WriteCommentRequest request) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		Writer writer = Writer.of(request.writerId(), request.writerName());
		Comment comment = Comment.of(writer, review, request.content());
		review.writeComment(comment);

		return comment.getId();
	}

	@Override
	@Transactional
	public void deleteComment(String reviewId, String commentId, DeleteCommentRequest request) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		review.deleteComment();
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_COMMENT));
		comment.delete(request.writerId());
	}

}
