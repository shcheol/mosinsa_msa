package com.mosinsa.review.query.application;

import com.mosinsa.review.query.application.dto.CommentSummaryDto;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.mosinsa.review.ui.reqeust.ReviewSummaryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryService {
	Page<ReviewSummaryDto> findProductReviews(ReviewSummaryRequest request, Pageable pageable);

	Page<CommentSummaryDto> findReviewComments(String reviewId, Pageable pageable);
}
