package com.mosinsa.customer.service;

import com.mosinsa.customer.entity.Customer;
import com.mosinsa.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;

    @Transactional
    public Long join(Customer customer){
        if(validateDuplicateCustomer(customer)){
            return null;
        }
        repository.save(customer);
        return customer.getId();
    }

    private boolean validateDuplicateCustomer(Customer customer) {
//        if(repository.findCustomerByLoginId(customer.getLoginId()).isPresent()){
//            throw new IllegalStateException("이미 존재하는 Id입니다.");
//        }
        return repository.findCustomerByLoginId(customer.getLoginId()).isPresent();
    }

    @Override
    public Customer login(String loginId, String password) {

        return repository.findCustomerByLoginId(loginId)
                .filter(customer -> customer.getPassword().equals(password)).orElse(null);

    }

    public Customer findById(Long customerId){
        return repository.findById(customerId).orElse(null);
    }

}
