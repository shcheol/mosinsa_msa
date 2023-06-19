package com.mosinsa.customer.service;

import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.web.controller.request.RequestCreateCustomer;

public interface CustomerService {

    Long join(RequestCreateCustomer requestCustomer);

    Long join(Customer customer);

    Customer findByLoginId(String loginId);

    Customer login(String loginId, String password);
}
