package com.mosinsa.review.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

class WriterTest {

	@Test
	void equalsAndHashcode() {

		Writer a = Writer.of("id", "name");
		Writer b = Writer.of("id", "name");

		Writer protectedConstructor = new Writer();

		Writer c = Writer.of("idxxx", "name");
		Writer d = Writer.of("id", "namexxx");

		EqualsAndHashcodeUtils.equalsAndHashcode(a, b, protectedConstructor, c, d);
	}

}