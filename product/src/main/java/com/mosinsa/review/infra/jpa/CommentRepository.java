package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
