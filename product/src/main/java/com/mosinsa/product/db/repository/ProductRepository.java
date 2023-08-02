package com.mosinsa.product.db.repository;

import com.mosinsa.product.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}

