package com.mosinsa.reaction.command.application;

import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;

public interface ReactionService {
	String reaction(ReactionSearchCondition condition);

	String cancel(ReactionSearchCondition condition);
}
