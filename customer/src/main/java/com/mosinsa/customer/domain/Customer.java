package com.mosinsa.customer.domain;

import com.mosinsa.customer.infra.kafka.CustomerCreatedEvent;
import com.mosinsa.customer.infra.kafka.KafkaEvents;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
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
		KafkaEvents.raise(new CustomerCreatedEvent(customer.id.getId()));
		return customer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Customer customer = (Customer) o;
		return id != null && Objects.equals(id, customer.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
