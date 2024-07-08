package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

	@Query(value = "select new com.mosinsa.review.query.application.dto.ReviewSummaryDto(r) " +
			" from Review r where r.productId = :productId and r.deleted = false order by r.createdDate desc")
	Page<ReviewSummaryDto> findReviewsByProductId(@Param(value = "productId") String productId, Pageable pageable);

	@Query(value = "select r from Review r join fetch r.comments where r.reviewId = :reviewId")
	Review findReviewByIdAndCommentsJoinFetch(@Param(value = "reviewId") ReviewId reviewId);

	@Query(value = "select r,c from Review r join Comment c on r.reviewId = c.review.reviewId where r.reviewId = :reviewId")
	List<Tuple> findReviewByIdAndComments(@Param(value = "reviewId") ReviewId reviewId);
}
