package com.mosinsa.reaction.qeury.application;

import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.jpa.ReactionRepository;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReactionReader {

	private final ReactionRepository repository;

	public boolean hasReacted(ReactionSearchCondition condition) {
		Reaction reaction = repository.findReactionByCondition(condition).orElse(null);
		return reaction != null && reaction.isActive();
	}

	public long total(ReactionSearchCondition condition) {
		return repository.countByCondition(condition);
	}
}
