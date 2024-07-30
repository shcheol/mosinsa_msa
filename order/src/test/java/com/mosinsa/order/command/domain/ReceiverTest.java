package com.mosinsa.order.command.domain;

import com.mosinsa.order.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiverTest {


	@Test
	void equalsAndHashCode() {
		String name = "name";
		String phoneNumber = "010-0000-0000";
		Receiver actual = Receiver.of(name, phoneNumber);
		Receiver expect = Receiver.of(name, phoneNumber);

		Receiver protectedConstructor = new Receiver();
		Receiver otherReceiver = Receiver.of("you", "");
		Receiver otherReceiver2 = Receiver.of("name", "");
		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(actual, expect, protectedConstructor, otherReceiver2, otherReceiver);
		assertThat(b).isTrue();

	}

}