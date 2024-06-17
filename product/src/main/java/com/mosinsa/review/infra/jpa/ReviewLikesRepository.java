package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewLikesRepository extends JpaRepository<ReviewLikes, String> {

    @Modifying
    @Query(value = "delete from ReviewLikes rl where rl.review = :review and rl.memberId = :memberId")
    void deleteReviewLikesByMemberId(@Param("memberId") String memberId, @Param("review") Review review);
}
