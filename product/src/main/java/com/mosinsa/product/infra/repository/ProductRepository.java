package com.mosinsa.product.infra.repository;

import com.mosinsa.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}

