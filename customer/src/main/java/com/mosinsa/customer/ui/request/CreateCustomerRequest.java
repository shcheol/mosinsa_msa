package com.mosinsa.customer.ui.request;

public record CreateCustomerRequest(String loginId, String password, String name, String email, String city,
									String street, String zipcode) {

}
