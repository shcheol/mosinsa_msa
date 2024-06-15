package com.mosinsa.review.command.application;

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
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final CommentRepository commentRepository;

	private final LikesService likesService;

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
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        review.delete(request.writerId());
    }

    @Transactional
    public String writeComment(String reviewId, WriteCommentRequest request) {
        Review review = reviewRepository.findById(ReviewId.of(reviewId))
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        Writer writer = Writer.of(request.writerId(), request.writerName());
        Comment savedComment = commentRepository.save(Comment.of(writer, review, request.content()));
        return savedComment.getId();
    }

    @Transactional
    public void deleteComment(String reviewId, String commentId, DeleteCommentRequest request) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        comment.delete(request.writerId());
    }

	@Transactional
	public void likesComment(String reviewId, String commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("not found"));
		likesService.likesComment(commentId);
	}

	@Transactional
	public void dislikesComment(String reviewId, String commentId) {
		likesService.dislikesComment(commentId);
	}

}
