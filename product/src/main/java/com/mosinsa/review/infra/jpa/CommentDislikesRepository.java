package com.mosinsa.review.infra.jpa;

import com.mosinsa.review.command.domain.CommentDislikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDislikesRepository extends JpaRepository<CommentDislikes, String> {
}
