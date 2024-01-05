package com.mosinsa.customer.application;

import com.mosinsa.customer.dto.CustomerDto;
import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.ui.request.CreateCustomerRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

	String join(CreateCustomerRequest requestCustomer);

    Customer findByLoginId(String loginId);

    Customer login(String loginId, String password);

	CustomerDto customerDetails(String customerId);

}
