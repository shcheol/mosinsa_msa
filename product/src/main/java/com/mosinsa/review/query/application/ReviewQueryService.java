package com.mosinsa.review.query.application;

import com.mosinsa.review.infra.jpa.CommentRepository;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import com.mosinsa.review.query.application.dto.CommentSummaryDto;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.mosinsa.review.ui.reqeust.ReviewSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewQueryService {

	private final ReviewRepository reviewRepository;

	private final CommentRepository commentRepository;

	public Page<ReviewSummaryDto> findProductReviews(ReviewSummaryRequest request, Pageable pageable){
		return reviewRepository.findReviewsByProductId(request.productId(), pageable);
	}

	public Page<CommentSummaryDto> findReviewComments(String reviewId, Pageable pageable){
		return commentRepository.findCommentsByReviewId(reviewId, pageable);
	}
}
