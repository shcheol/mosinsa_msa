package com.mosinsa.review.ui;

import com.mosinsa.review.query.application.dto.CommentSummaryDto;
import com.mosinsa.review.query.application.ReviewQueryService;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.mosinsa.review.ui.reqeust.ReviewSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ViewReviewController {

	private final ReviewQueryService reviewQueryService;

	@GetMapping
	public ResponseEntity<Page<ReviewSummaryDto>> productReviews(@ModelAttribute ReviewSummaryRequest request, Pageable pageable) {

		Page<ReviewSummaryDto> productReviews = reviewQueryService.findProductReviews(request, pageable);
		return ResponseEntity.ok(productReviews);
	}

	@GetMapping("/{reviewId}/comments")
	public ResponseEntity<Page<CommentSummaryDto>> reviewComments(@PathVariable("reviewId") String reviewId, Pageable pageable) {

		Page<CommentSummaryDto> reviewComments = reviewQueryService.findReviewComments(reviewId, pageable);
		return ResponseEntity.ok(reviewComments);
	}

}
