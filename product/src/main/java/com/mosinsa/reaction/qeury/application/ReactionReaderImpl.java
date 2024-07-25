package com.mosinsa.reaction.qeury.application;

import com.mosinsa.reaction.command.domain.Reaction;
import com.mosinsa.reaction.command.domain.ReactionInfo;
import com.mosinsa.reaction.infra.jpa.ReactionInfoRepository;
import com.mosinsa.reaction.infra.jpa.ReactionRepository;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReactionReaderImpl implements ReactionReader {

    private final ReactionRepository repository;
    private final ReactionInfoRepository infoRepository;


    public boolean hasReacted(ReactionSearchCondition condition) {
        Reaction reaction = repository.findReactionByCondition(condition).orElse(null);
        return reaction != null && reaction.isActive();
    }

    public long total(ReactionSearchCondition condition) {
        return infoRepository.findReactionInfoByCondition(condition)
                .orElseGet(() -> infoRepository.save(ReactionInfo.of(condition.target(), condition.targetId(), condition.reactionType())))
                .getTotal();
    }
}
