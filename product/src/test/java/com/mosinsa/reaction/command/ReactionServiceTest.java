package com.mosinsa.reaction.command;

import com.mosinsa.reaction.domain.ReactionType;
import com.mosinsa.reaction.domain.TargetEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReactionServiceTest {

	@Autowired ReactionService service;

	@Test
	void likes() {
		ReactionRequest reactionRequest = new ReactionRequest(TargetEntity.COMMENT, "commentId1", ReactionType.LIKES, "memberId1");
		service.likes(reactionRequest);
	}
}