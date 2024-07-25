package com.mosinsa.review.ui;

import com.mosinsa.review.command.application.ReviewService;
import com.mosinsa.review.ui.reqeust.DeleteCommentRequest;
import com.mosinsa.review.ui.reqeust.DeleteReviewRequest;
import com.mosinsa.review.ui.reqeust.WriteCommentRequest;
import com.mosinsa.review.ui.reqeust.WriteReviewRequest;

public class ReviewServiceStub implements ReviewService {

	@Override
	public String writeReview(WriteReviewRequest request) {
		return "reviewId";
	}

	@Override
	public void deleteReview(String reviewId, DeleteReviewRequest request) {

	}

	@Override
	public String writeComment(String reviewId, WriteCommentRequest request) {
		return "commentId";
	}

	@Override
	public void deleteComment(String reviewId, String commentId, DeleteCommentRequest request) {

	}
}
