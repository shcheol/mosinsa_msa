package com.mosinsa.review.command.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WriterTest {

	@Test
	void equalsAndHashcode(){

		Writer a = Writer.of("id", "name");
		Writer b = Writer.of("id", "name");
		assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);

	}

}