package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, String> {
}
