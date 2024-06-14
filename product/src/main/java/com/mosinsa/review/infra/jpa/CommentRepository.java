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

	@Query("select c from Comment c where c.id = :commentId and c.deleted = false")
	Optional<Comment> findByIdNotDeleted(@Param("commentId") String commentId);
	@Query("select c from Comment c where c.review.reviewId.id = :reviewId order by c.createdDate desc")
	Page<CommentSummaryDto> findCommentsByReviewId(@Param("reviewId") String reviewId, Pageable pageable);
}
