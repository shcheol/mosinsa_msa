package com.mosinsa.reaction.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReactionIdTest {
	@Test
	void idCreateFail(){
		assertThrows(IllegalArgumentException.class, () -> ReactionId.of(null));
		assertThrows(IllegalArgumentException.class, () -> ReactionId.of(""));
	}

	@Test
	void idCreateSuccess(){
		String value = "id";
		ReactionId id = ReactionId.of(value);
		assertThat(id.getId()).isEqualTo(value);
	}


	@Test
	void idEqualsAndHashCode(){
		String value = "id";
		ReactionId idA = ReactionId.of(value);
		ReactionId idB = ReactionId.of(value);

		ReactionId idC = ReactionId.of("idxx");

		ReactionId protectedConstructor = new ReactionId();

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(idA, idB, protectedConstructor, idC);
		assertThat(b).isTrue();
	}
}