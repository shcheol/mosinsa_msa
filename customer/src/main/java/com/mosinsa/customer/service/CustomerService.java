package com.mosinsa.customer.service;

import com.mosinsa.customer.db.dto.CustomerDto;
import com.mosinsa.customer.db.entity.Customer;
import com.mosinsa.customer.web.controller.request.RequestCreateCustomer;

public interface CustomerService {

    Long join(RequestCreateCustomer requestCustomer);

    Long join(Customer customer);

    Customer findByLoginId(String loginId);

    Customer login(String loginId, String password);

	Customer findById(Long customerId);

	CustomerDto getCustomerDetailsByCustomerId(Long customerId);

	void delete(Long customerId);
}
