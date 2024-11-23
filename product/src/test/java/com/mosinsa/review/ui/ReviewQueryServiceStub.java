package com.mosinsa.review.ui;

import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.Writer;
import com.mosinsa.review.query.application.ReviewQueryService;
import com.mosinsa.review.query.application.dto.CommentSummaryDto;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.mosinsa.review.ui.reqeust.ReviewSummaryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ReviewQueryServiceStub implements ReviewQueryService {
	@Override
	public Page<ReviewSummaryDto> findProductReviews(ReviewSummaryRequest request, Pageable pageable) {
		return new PageImpl<>(
				List.of(
						new ReviewSummaryDto(Review.of(Writer.of("", ""), request.productId(), "")),
						new ReviewSummaryDto(Review.of(Writer.of("", ""), request.productId(), ""))
				), PageRequest.of(0,10),0);
	}

	@Override
	public Page<CommentSummaryDto> findReviewComments(String reviewId, Pageable pageable) {
		Review of = Review.of(Writer.of("", ""), "productId", "");
		return new PageImpl<>(
				List.of(
						new CommentSummaryDto(Comment.of(Writer.of("", ""), of, "")),
						new CommentSummaryDto(Comment.of(Writer.of("", ""), of, ""))
				), PageRequest.of(0,10),0);
	}
}
