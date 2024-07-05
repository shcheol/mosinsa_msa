package com.mosinsa.reaction.command.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Entity
public class Reaction extends BaseEntity {

	@EmbeddedId
	private ReactionId id;

	@Enumerated(value = EnumType.STRING)
	private TargetEntity targetType;

	private String targetId;

	@Enumerated(value = EnumType.STRING)
	private ReactionType reactionType;

	private String memberId;

	protected Reaction() {
	}

	public static Reaction of(TargetEntity targetType, String targetId, ReactionType reactionType, String memberId) {
		Reaction reaction = new Reaction();
		reaction.id = ReactionId.newId();
		reaction.memberId = memberId;
		reaction.targetType = targetType;
		reaction.targetId = targetId;
		reaction.reactionType = reactionType;
		return reaction;
	}

	public void active(){
		super.active();
	}

	public void delete(){
		super.delete();
	}
}
