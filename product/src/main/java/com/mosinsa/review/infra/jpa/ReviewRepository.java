package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.query.application.dto.ReviewSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

	@Query(value = "select new com.mosinsa.review.query.application.dto.ReviewSummaryDto(r) " +
			" from Review r where r.productId = :productId and r.deleted = false order by r.createdDate desc")
	Page<ReviewSummaryDto> findReviewsByProductId(@Param(value = "productId") String productId, Pageable pageable);
}
