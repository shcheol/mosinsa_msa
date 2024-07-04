package com.mosinsa.reaction.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class ReactionInfo {

	@Id
	@Column(name = "reaction_info_id")
	private String id;

	@Enumerated(EnumType.STRING)
	private TargetEntity targetType;
	private String targetId;
	@Enumerated(EnumType.STRING)
	private ReactionType reactionType;

	private Long total;

	public long increase(){
		total+=1;
		return total;
	}

	public long decrease(){
		total -=1;
		return total;
	}
	protected ReactionInfo() {
	}

	public static ReactionInfo of(TargetEntity target, String targetId, ReactionType reactionType) {
		ReactionInfo reactionInfo = new ReactionInfo();
		reactionInfo.id = UUID.randomUUID().toString();
		reactionInfo.targetType = target;
		reactionInfo.targetId = targetId;
		reactionInfo.reactionType = reactionType;
		reactionInfo.total = 0L;
		return reactionInfo;
	}
}
