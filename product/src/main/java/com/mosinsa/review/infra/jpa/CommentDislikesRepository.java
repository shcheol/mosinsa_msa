package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.CommentDislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentDislikesRepository extends JpaRepository<CommentDislikes, String> {

	@Modifying
	@Query(value = "delete from CommentDislikes cd where cd.comment = :comment and cd.memberId = :memberId")
	void deleteCommentDislikesByMemberId(@Param("memberId") String memberId, @Param("comment") Comment comment);
}
