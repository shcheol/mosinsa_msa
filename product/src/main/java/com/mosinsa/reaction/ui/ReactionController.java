package com.mosinsa.reaction.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.reaction.command.application.ReactionService;
import com.mosinsa.reaction.infra.kafka.ProduceTemplate;
import com.mosinsa.reaction.qeury.application.dto.ReactionSearchCondition;
import com.mosinsa.reaction.ui.request.ReactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor
public class ReactionController {

	private final ReactionService reactionService;
	private final ProduceTemplate produceTemplate;

	@PostMapping
	public ResponseEntity<String> addUserReaction(@RequestBody ReactionRequest request, @Login CustomerInfo customerInfo){

		ReactionSearchCondition condition =
				new ReactionSearchCondition(request.target(), request.targetId(), request.reactionType(), customerInfo.id());
		String reaction = reactionService.reaction(condition);
		produceTemplate.produce(condition.target(), condition.targetId(), condition.reactionType(), false);
		return ResponseEntity.ok(reaction);
	}

	@PostMapping("/cancel")
	public ResponseEntity<String> cancelUserReaction(@RequestBody ReactionRequest request, @Login CustomerInfo customerInfo){

		ReactionSearchCondition condition =
				new ReactionSearchCondition(request.target(), request.targetId(), request.reactionType(), customerInfo.id());
		String reaction = reactionService.cancel(condition);
		produceTemplate.produce(condition.target(), condition.targetId(), condition.reactionType(), true);
		return ResponseEntity.ok(reaction);
	}

}
