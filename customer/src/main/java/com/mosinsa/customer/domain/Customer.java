package com.mosinsa.customer.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @EmbeddedId
    private CustomerId id;

	@Valid
	@Embedded
	private Certification cert;

	@NotBlank
	private String name;

	@NotBlank
	@Email
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerGrade grade;

	@Valid
	@Embedded
	private Address address;

	public static Customer create(String loginId, String password, String name, String email,
								  String city, String street, String zipcode){

		Customer customer = new Customer();
		customer.id = CustomerId.newId();
		customer.cert = Certification.create(loginId, password);
		customer.name = name;
		customer.email = email;
		customer.grade = CustomerGrade.BRONZE;
		customer.address = Address.of(city, street, zipcode);
		return customer;
	}

}
