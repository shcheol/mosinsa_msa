package com.mosinsa.customer.service;

import com.mosinsa.customer.entity.Customer;

public interface CustomerService {

    Long join(Customer customer);

    Customer findByLoginId(String loginId);

    Customer login(String loginId, String password);
}
