package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

	@Test
	void equalsAndHashCode() {
		String zipcode = "zipcode";
		String address1 = "address1";
		String address2 = "address2";
		AddressDto dto = new AddressDto(zipcode, address1, address2);
		Address actual = Address.of(dto);
		Address expect = Address.of(dto);
		assertThat(actual).isNotNull();
		assertThat(actual.getZipCode()).isEqualTo(zipcode);
		assertThat(actual.getAddress1()).isEqualTo(address1);
		assertThat(actual.getAddress2()).isEqualTo(address2);
		assertThat(actual).isEqualTo(actual).isEqualTo(expect).hasSameHashCodeAs(expect)
				.isNotEqualTo(null).isNotEqualTo(new TestClass())
				.isNotEqualTo(Address.of(new AddressDto("zipcodexxx", "address1", "address2")))
				.isNotEqualTo(Address.of(new AddressDto("zipcode", "address1xxx", "address2")))
				.isNotEqualTo(Address.of(new AddressDto("zipcode", "address1", "address2xxx")));
	}

}