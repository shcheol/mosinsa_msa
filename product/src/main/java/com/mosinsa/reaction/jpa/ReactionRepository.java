package com.mosinsa.reaction.jpa;

import com.mosinsa.reaction.domain.Reaction;
import com.mosinsa.reaction.domain.ReactionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {
}
