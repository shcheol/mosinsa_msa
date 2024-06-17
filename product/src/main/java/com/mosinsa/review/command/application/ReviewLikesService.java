package com.mosinsa.review.command.application;

import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.review.command.domain.*;
import com.mosinsa.review.infra.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewLikesService {

	private final ReviewLikesRepository reviewLikesRepository;

	private final ReviewDislikesRepository reviewDislikesRepository;

	private final ReviewRepository reviewRepository;


	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void likesReview(String reviewId, String customerId) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		ReviewLikes commentLikes = ReviewLikes.create(customerId, review);
		reviewLikesRepository.save(commentLikes);
		review.likes();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void likesReviewCancel(String reviewId, String customerId) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		reviewLikesRepository.deleteReviewLikesByMemberId(customerId, review);
		review.likesCancel();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void dislikesReview(String reviewId, String customerId) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		ReviewDislikes commentDislikes = ReviewDislikes.create(customerId, review);
		reviewDislikesRepository.save(commentDislikes);
		review.dislikes();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void dislikesReviewCancel(String reviewId, String customerId) {
		Review review = reviewRepository.findById(ReviewId.of(reviewId))
				.orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW));
		reviewDislikesRepository.deleteReviewDislikesByMemberId(customerId, review);
		review.dislikesCancel();
	}
}
