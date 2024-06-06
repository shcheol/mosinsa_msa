package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Review;
import com.mosinsa.review.command.domain.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
}
