package com.mosinsa.customer.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	@NotBlank
	private String city;
	@NotBlank
	private String street;

	@NotBlank
	private String zipcode;

	protected static Address of(String city, String street, String zipcode) {
		Address address = new Address();
		address.city = city;
		address.street = street;
		address.zipcode = zipcode;
		return address;
	}
}
