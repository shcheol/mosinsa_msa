package com.mosinsa.reaction.command.application;

import com.mosinsa.common.aop.RedissonLock;
import com.mosinsa.reaction.command.domain.ReactionInfo;
import com.mosinsa.reaction.infra.jpa.ReactionInfoRepository;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionService {

	private final ReactionProcessor processor;

	private final ReactionInfoRepository repository;

	private final String REACTION_LOCK_KEY= "reaction_lock_key";

	@RedissonLock(value = REACTION_LOCK_KEY)
	@Transactional
	public String reaction(ReactionSearchCondition condition){

		String reaction = processor.reaction(condition);
		ReactionInfo reactionInfo = repository.findReactionInfoByCondition(condition)
				.orElseGet(() -> repository.save(ReactionInfo.of(condition.target(), condition.targetId(), condition.reactionType())));
		reactionInfo.increase();

		return reaction;
	}

	@Transactional
	@RedissonLock(value = REACTION_LOCK_KEY)
	public String cancel(ReactionSearchCondition condition){
		String cancel = processor.cancel(condition);
		ReactionInfo reactionInfo = repository.findReactionInfoByCondition(condition)
				.orElseGet(() -> repository.save(ReactionInfo.of(condition.target(), condition.targetId(), condition.reactionType())));
		reactionInfo.decrease();

		return cancel;
	}

}
