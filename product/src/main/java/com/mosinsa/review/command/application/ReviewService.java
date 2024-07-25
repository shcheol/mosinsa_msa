package com.mosinsa.review.command.application;

import com.mosinsa.review.ui.reqeust.DeleteCommentRequest;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteCommentRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;

public interface ReviewService {
	String writeReview(WriteReviewRequest request);

	void deleteReview(String reviewId, DeleteReviewRequest request);

	String writeComment(String reviewId, WriteCommentRequest request);

	void deleteComment(String reviewId, String commentId, DeleteCommentRequest request);
}
