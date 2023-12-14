package com.mosinsa.customer.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

	@Test
	void create() {
		Customer customer = Customer.create(
				"loginId",
				"password",
				"name",
				"email@email.com",
				"city", "street", "zipcode");

		assertThat(customer.getCert()).isEqualTo(Certification.create("loginId","password"));
		assertThat(customer.getName()).isEqualTo("name");
		assertThat(customer.getEmail()).isEqualTo("email@email.com");
		assertThat(customer.getAddress()).isEqualTo(Address.of("city", "street", "zipcode"));
		assertThat(customer.getGrade()).isEqualTo(CustomerGrade.BRONZE);

	}

}