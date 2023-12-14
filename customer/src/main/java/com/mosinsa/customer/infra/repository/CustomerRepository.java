package com.mosinsa.customer.infra.repository;

import com.mosinsa.customer.domain.Customer;
import com.mosinsa.customer.domain.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, CustomerId> {

	@Query(value = "select c from Customer c where c.cert.loginId = :loginId")
    Optional<Customer> findCustomerByLoginId(@Param(value = "loginId") String loginId);
}
