package com.mosinsa.reaction;

import com.mosinsa.reaction.command.application.ReactionProcessor;
import com.mosinsa.reaction.command.application.ReactionService;
import com.mosinsa.reaction.infra.jpa.ReactionInfoRepository;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;

public class ReactionServiceStub extends ReactionService {
    public ReactionServiceStub(ReactionProcessor processor, ReactionInfoRepository repository) {
        super(processor, repository);
    }

    @Override
    public String reaction(ReactionSearchCondition condition) {
        return "reaction";
    }

    @Override
    public String cancel(ReactionSearchCondition condition) {
        return "cancel";
    }
}
