package com.mosinsa.customer.infra.repository;

import com.mosinsa.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findCustomerByLoginId(String loginId);

    Optional<Object> findByLoginId(String loginId);
}
