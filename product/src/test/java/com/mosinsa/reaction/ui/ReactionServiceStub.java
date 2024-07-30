package com.mosinsa.reaction.ui;

import com.mosinsa.reaction.command.application.ReactionService;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;

public class ReactionServiceStub implements ReactionService {
	@Override
	public String reaction(ReactionSearchCondition condition) {
		return "reaction";
	}

	@Override
	public String cancel(ReactionSearchCondition condition) {
		return "cancel";
	}
}
