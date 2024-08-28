package com.mosinsa.reaction.command.domain;

import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReactionRepository extends Repository<Reaction, ReactionId> {

	Reaction save(Reaction reaction);

	Optional<Reaction> findById(ReactionId reactionId);

	@Query(value = "select r from Reaction r " +
			"where r.targetType = :#{#condition.target()} " +
			"and r.targetId = :#{#condition.targetId()} " +
			"and r.reactionType = :#{#condition.reactionType()} " +
			"and r.memberId = :#{#condition.memberId()}")
	Optional<Reaction> findReactionByCondition(@Param("condition") ReactionSearchCondition condition);

}
