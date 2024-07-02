package com.mosinsa.reaction.command;

import com.mosinsa.reaction.domain.Reaction;
import com.mosinsa.reaction.jpa.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionService {
	private final ReactionRepository repository;

	public void likes(ReactionRequest request){



		Reaction of = Reaction.of(request.target(), request.targetId(), request.reactionType(), request.memberId());


		Reaction save = repository.save(of);



	}

	public void likesCancel(ReactionRequest request){

	}

}
