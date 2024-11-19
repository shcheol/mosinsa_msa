package com.mosinsa.reaction.command.application;

import com.mosinsa.common.aop.RedissonLock;
import com.mosinsa.common.event.Events;
import com.mosinsa.common.event.ReactionCanceledEvent;
import com.mosinsa.common.event.ReactionReactedEvent;
import com.mosinsa.reaction.command.domain.ReactionInfo;
import com.mosinsa.reaction.command.domain.ReactionInfoRepository;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

	private final ReactionProcessor processor;
	private final ReactionInfoRepository repository;
	private static final String REACTION_LOCK_KEY = "reaction_lock_key";

	@Override
	@RedissonLock(value = REACTION_LOCK_KEY)
	@Transactional
	public String reaction(ReactionSearchCondition condition) {

		String reaction = processor.reaction(condition);
		ReactionInfo reactionInfo = repository.findReactionInfoByCondition(condition)
				.orElseGet(() -> repository.save(ReactionInfo.of(condition.target(), condition.targetId(), condition.reactionType())));
		reactionInfo.increase();
		Events.raise(new ReactionReactedEvent(condition.target(), condition.targetId(), condition.reactionType()));
		return reaction;
	}

	@Override
	@Transactional
	@RedissonLock(value = REACTION_LOCK_KEY)
	public String cancel(ReactionSearchCondition condition) {
		String cancel = processor.cancel(condition);
		ReactionInfo reactionInfo = repository.findReactionInfoByCondition(condition)
				.orElseGet(() -> repository.save(ReactionInfo.of(condition.target(), condition.targetId(), condition.reactionType())));
		reactionInfo.decrease();
		Events.raise(new ReactionCanceledEvent(condition.target(), condition.targetId(), condition.reactionType()));
		return cancel;
	}

}
