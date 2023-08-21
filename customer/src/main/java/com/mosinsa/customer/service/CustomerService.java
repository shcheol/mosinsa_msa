package com.mosinsa.customer.service;

import com.mosinsa.customer.db.dto.CustomerDto;
import com.mosinsa.customer.db.entity.Customer;
import com.mosinsa.customer.web.controller.request.RequestCreateCustomer;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

	String join(RequestCreateCustomer requestCustomer);

	String join(Customer customer);

    Customer findByLoginId(String loginId);

    Customer login(String loginId, String password);

	Customer findById(String customerId);

	CustomerDto getCustomerDetailsByCustomerId(String customerId);

	void delete(String customerId);
}
