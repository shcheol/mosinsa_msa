package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewDislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewDislikesRepository extends JpaRepository<ReviewDislikes, String> {

	@Modifying
	@Query(value = "delete from ReviewDislikes rd where rd.review = :review and rd.memberId = :memberId")
	void deleteReviewDislikesByMemberId(@Param("memberId") String memberId, @Param("review") Review review);
}
