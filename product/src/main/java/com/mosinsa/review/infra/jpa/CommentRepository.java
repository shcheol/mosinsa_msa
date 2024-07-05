package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.query.application.dto.CommentSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {

	@Query("select c from Comment c where c.review.reviewId.id = :reviewId order by c.createdDate asc")
	Page<CommentSummaryDto> findCommentsByReviewId(@Param("reviewId") String reviewId, Pageable pageable);

	@Query("select r.productId from Comment c join Review r on c.review = r where c.id = :commentId")
	Optional<String> findProductId(@Param("commentId") String commentId);
}
