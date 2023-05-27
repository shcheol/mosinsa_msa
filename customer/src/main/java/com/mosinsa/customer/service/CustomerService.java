package com.mosinsa.customer.service;

import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public Long join(Customer customer){
        validateDuplicateCustomer(customer);
        repository.save(customer);
        return customer.getId();
    }

    private void validateDuplicateCustomer(Customer customer) {

    }
}
