package com.mosinsa.customer.service;

import com.mosinsa.customer.db.entity.Customer;
import com.mosinsa.customer.db.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void join(){
        Customer customer = new Customer();
        customer.setLoginId("loginId");
        customer.setName("name");
        customer.setPassword("password");
        customer.setEmail("test@test.com");

        Long join = customerService.join(customer);

        assertEquals(customer,customerRepository.findById(join).get());
    }

    @Test
    public void join_exception(){
        Customer customer = new Customer();
        customer.setLoginId("loginId");
        customer.setName("name");
        customer.setPassword("password");
        customer.setEmail("test@test.com");

        customerService.join(customer);

        Customer customer2 = new Customer();
        customer2.setLoginId("loginId");
        customer.setName("name");
        customer2.setPassword("password");
        customer2.setEmail("test@test.com");

        assertThatThrownBy(() ->customerService.join(customer2)).isInstanceOf(IllegalStateException.class);

    }

}