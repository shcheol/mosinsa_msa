package com.mosinsa.review.query.application.dto;

import com.mosinsa.review.command.domain.Review;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReviewSummaryDto {
	String reviewId;
	String writerId;
	String writer;
	String contents;
	LocalDateTime createdDate;

	@QueryProjection
	public ReviewSummaryDto(Review review) {
		this.reviewId = review.getReviewId().getId();
		this.writerId = review.getWriter().getWriterId();
		this.writer = review.getWriter().getName();
		this.contents = review.isDeleted() ? "삭제된 리뷰입니다." : review.getContents();
		this.createdDate = review.getCreatedDate();
	}
}
