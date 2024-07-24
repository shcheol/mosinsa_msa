package com.mosinsa.reaction.command.domain;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReactionTest {

	@Test
	void equalsAndHashCode(){
		Reaction reactionA = Reaction.of(TargetEntity.PRODUCT, "id", ReactionType.LIKES, "member");

		assertThat(reactionA).isEqualTo(reactionA).hasSameHashCodeAs(reactionA)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		Reaction reactionC = Reaction.of(TargetEntity.PRODUCT, "id3", ReactionType.LIKES, "member");
		assertThat(reactionA).isNotEqualTo(reactionC).doesNotHaveSameHashCodeAs(reactionC);

		Reaction protectedConstructor = new Reaction();
		assertThat(protectedConstructor.hashCode()).isZero();
	}

}