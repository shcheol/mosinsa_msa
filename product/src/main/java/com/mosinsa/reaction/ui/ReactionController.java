package com.mosinsa.reaction.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.reaction.command.application.ReactionService;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
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

	@PostMapping
	public ResponseEntity<String> addUserReaction(@RequestBody ReactionRequest request, @Login CustomerInfo customerInfo){

		ReactionSearchCondition condition =
				new ReactionSearchCondition(request.target(), request.targetId(), request.reactionType(), customerInfo.id());
		String reaction = reactionService.reaction(condition);
		return ResponseEntity.ok(reaction);
	}

	@PostMapping("/cancel")
	public ResponseEntity<String> cancelUserReaction(@RequestBody ReactionRequest request, @Login CustomerInfo customerInfo){

		ReactionSearchCondition condition =
				new ReactionSearchCondition(request.target(), request.targetId(), request.reactionType(), customerInfo.id());
		String reaction = reactionService.cancel(condition);
		return ResponseEntity.ok(reaction);
	}

}
