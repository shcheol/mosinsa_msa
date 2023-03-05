package com.shopping.mosinsa.repository;

import com.shopping.mosinsa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
