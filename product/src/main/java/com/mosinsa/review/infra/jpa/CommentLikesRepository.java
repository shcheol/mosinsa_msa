package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Comment;
import com.mosinsa.review.command.domain.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, String> {

    @Query(value = "select cl from CommentLikes cl where cl.comment = :comment and cl.memberId = :memberId")
    void findCommentLikesByMemberIdAndComment(@Param("memberId") String memberId, @Param("likes") CommentLikes likes);

    @Modifying
    @Query(value = "delete from CommentLikes cl where cl.comment = :likes and cl.memberId = :memberId")
    void deleteCommentLikesByMemberId(@Param("memberId") String memberId, @Param("likes") Comment likes);
}
