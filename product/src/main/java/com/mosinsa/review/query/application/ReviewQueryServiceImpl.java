package com.mosinsa.review.query.application;

import com.mosinsa.review.command.domain.CommentRepository;
import com.mosinsa.review.command.domain.ReviewRepository;
import com.mosinsa.review.query.application.dto.CommentSummaryDto;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.mosinsa.review.ui.reqeust.ReviewSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

	private final ReviewRepository reviewRepository;

	private final CommentRepository commentRepository;

	@Override
	public Page<ReviewSummaryDto> findProductReviews(ReviewSummaryRequest request, Pageable pageable) {
		return reviewRepository.findReviewsByProductId(request.productId(), pageable);
	}

	@Override
	public Page<CommentSummaryDto> findReviewComments(String reviewId, Pageable pageable) {
		return commentRepository.findCommentsByReviewId(reviewId, pageable);
	}
}
