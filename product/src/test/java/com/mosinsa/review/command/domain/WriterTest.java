package com.mosinsa.review.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WriterTest {

	@Test
	void equalsAndHashcode() {

		Writer a = Writer.of("id", "name");
		Writer b = Writer.of("id", "name");

		Writer protectedConstructor = new Writer();

		Writer c = Writer.of("idxxx", "name");
		Writer d = Writer.of("id", "namexxx");

		boolean b1 = EqualsAndHashcodeUtils.equalsAndHashcode(a, b, protectedConstructor, c, d);
		assertThat(b1).isTrue();
	}

}