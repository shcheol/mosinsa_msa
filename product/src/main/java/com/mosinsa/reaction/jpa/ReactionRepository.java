package com.mosinsa.reaction.jpa;

import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.command.domain.ReactionId;
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

	@Query(value = "select count(r) from Reaction r " +
			"where r.targetType = :#{#condition.target()} " +
			"and r.targetId = :#{#condition.targetId()} " +
			"and r.reactionType = :#{#condition.reactionType()} " +
			"and r.active = true")
	long countByCondition(@Param("condition") ReactionSearchCondition condition);
}
