package com.mosinsa.customer.db.repository;

import com.mosinsa.customer.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByLoginId(String loginId);

    Optional<Object> findByLoginId(String loginId);
}
