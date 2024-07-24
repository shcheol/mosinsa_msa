package com.mosinsa.review.command.domain;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WriterTest {

	@Test
	void equalsAndHashcode(){

		Writer a = Writer.of("id", "name");
		Writer b = Writer.of("id", "name");
		assertThat(a).isEqualTo(b).hasSameHashCodeAs(b)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());
		Writer protectedConstructor = new Writer();
		assertThat(protectedConstructor).isNotEqualTo(a).doesNotHaveSameHashCodeAs(a);

	}

}