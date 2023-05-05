package com.shopping.mosinsa.repository;

import com.shopping.mosinsa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

