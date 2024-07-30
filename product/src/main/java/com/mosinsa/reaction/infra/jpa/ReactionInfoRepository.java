package com.mosinsa.reaction.infra.jpa;

import com.mosinsa.reaction.command.domain.ReactionInfo;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReactionInfoRepository extends JpaRepository<ReactionInfo, String> {

	@Query(value = "select ri from ReactionInfo ri " +
			"where ri.targetType = :#{#condition.target()} " +
			"and ri.targetId = :#{#condition.targetId()} " +
			"and ri.reactionType = :#{#condition.reactionType()}")
	Optional<ReactionInfo> findReactionInfoByCondition(@Param("condition") ReactionSearchCondition condition);
}
