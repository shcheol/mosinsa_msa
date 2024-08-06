package com.mosinsa.order.command.domain;

import com.mosinsa.order.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

	@Test
	void equalsAndHashCode() {
		String zipcode = "zipcode";
		String address1 = "address1";
		String address2 = "address2";
		Address actual = Address.of(zipcode, address1, address2);
		Address expect = Address.of(zipcode, address1, address2);
		Address a = Address.of("zipcodexxx", address1, address2);
		Address b = Address.of(zipcode, "address1xxx", address2);
		Address c = Address.of("zipcode", address1, "address2xxx");

		Address protectedConstructor = new Address();
		boolean b1 = EqualsAndHashcodeUtils.equalsAndHashcode(actual, expect, protectedConstructor, a, b, c);
		assertThat(b1).isTrue();
	}

}