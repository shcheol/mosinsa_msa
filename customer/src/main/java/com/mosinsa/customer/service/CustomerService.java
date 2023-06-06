package com.mosinsa.customer.service;

import com.mosinsa.customer.entity.Customer;

public interface CustomerService {

    Long join(Customer customer);

    Customer login(String loginId, String password);
}
