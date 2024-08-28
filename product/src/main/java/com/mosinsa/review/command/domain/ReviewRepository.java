package com.mosinsa.review.command.domain;

import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends Repository<Review, ReviewId> {

	Review save(Review review);

	Optional<Review> findById(ReviewId reviewId);

	@Query(value = "select new com.mosinsa.review.query.application.dto.ReviewSummaryDto(r) " +
			" from Review r where r.productId = :productId and r.deleted = false order by r.createdDate desc")
	Page<ReviewSummaryDto> findReviewsByProductId(@Param(value = "productId") String productId, Pageable pageable);

	@Query(value = "select r from Review r join fetch r.comments where r.reviewId = :reviewId")
	Review findReviewByIdAndCommentsJoinFetch(@Param(value = "reviewId") ReviewId reviewId);

	@Query(value = "select r,c from Review r join Comment c on r.reviewId = c.review.reviewId where r.reviewId = :reviewId")
	List<Tuple> findReviewByIdAndComments(@Param(value = "reviewId") ReviewId reviewId);
}
