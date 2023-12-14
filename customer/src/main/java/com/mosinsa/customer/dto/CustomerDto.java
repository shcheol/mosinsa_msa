package com.mosinsa.customer.dto;

import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.domain.CustomerGrade;
import lombok.Value;

@Value
public class CustomerDto {

	String id;
	String name;
	String loginId;
	String email;
	CustomerGrade grade;
	String city;
	String street;
	String zipcode;

	public CustomerDto(Customer customer) {
		this.id = customer.getId().getId();
		this.loginId = customer.getCert().getLoginId();
		this.name = customer.getName();
		this.email = customer.getEmail();
		this.grade = customer.getGrade();
		this.city = customer.getAddress().getCity();
		this.street = customer.getAddress().getStreet();
		this.zipcode = customer.getAddress().getZipcode();
	}

}
