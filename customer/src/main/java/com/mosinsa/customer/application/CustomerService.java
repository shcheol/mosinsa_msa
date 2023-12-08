package com.mosinsa.customer.application;

import com.mosinsa.customer.dto.CustomerDto;
import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.ui.request.RequestCreateCustomer;
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
