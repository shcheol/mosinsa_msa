package com.mosinsa.customer.service;

import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;

    @Transactional
    public Long join(Customer customer){
        validateDuplicateCustomer(customer);
        repository.save(customer);
        return customer.getId();
    }

    @Override
    public Customer login(String loginId, String password) {

        return repository.findCustomerByLoginId(loginId)
                .filter(customer -> customer.getPassword().equals(password)).orElse(null);

    }

    private void validateDuplicateCustomer(Customer customer) {
        if(repository.findCustomerByLoginId(customer.getLoginId()).isPresent()){
            throw new IllegalStateException("이미 존재하는 Id입니다.");
        }
    }
}
