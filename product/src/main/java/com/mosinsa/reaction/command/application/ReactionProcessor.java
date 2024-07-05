package com.mosinsa.reaction.command.application;

import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.infra.jpa.ReactionRepository;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionProcessor {
	private final ReactionRepository repository;

	@Transactional
	public String reaction(ReactionSearchCondition condition){

		Reaction reaction = repository.findReactionByCondition(condition)
				.orElseGet(() -> repository.save(Reaction.of(condition.target(), condition.targetId(), condition.reactionType(), condition.memberId())));
		if (reaction.isActive()) {
			throw new AlreadySameStateException();
		}
		reaction.active();
		return reaction.getId().getId();
	}

	@Transactional
	public String cancel(ReactionSearchCondition condition){
		Reaction reaction = repository.findReactionByCondition(condition).orElseThrow();
		if (!reaction.isActive()) {
			throw new AlreadySameStateException();
		}
		reaction.delete();
		return reaction.getId().getId();
	}

}
