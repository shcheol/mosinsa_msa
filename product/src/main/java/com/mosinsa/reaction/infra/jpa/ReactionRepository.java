package com.mosinsa.reaction.infra.jpa;

import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.command.domain.ReactionId;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {

	@Query(value = "select r from Reaction r " +
			"where r.targetType = :#{#condition.target()} " +
			"and r.targetId = :#{#condition.targetId()} " +
			"and r.reactionType = :#{#condition.reactionType()} " +
			"and r.memberId = :#{#condition.memberId()}")
	Optional<Reaction> findReactionByCondition(@Param("condition") ReactionSearchCondition condition);

}
