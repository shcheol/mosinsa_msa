package com.mosinsa.reaction.command.domain;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

		assertThat(idA).isEqualTo(idA).isEqualTo(idB).hasSameHashCodeAs(idB)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		ReactionId idC = ReactionId.of("idxx");
		assertThat(idA).isNotEqualTo(idC).doesNotHaveSameHashCodeAs(idC);

		ReactionId protectedConstructor = new ReactionId();
		assertThat(protectedConstructor.hashCode()).isZero();
	}
}