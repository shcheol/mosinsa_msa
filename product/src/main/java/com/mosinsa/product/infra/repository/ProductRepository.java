package com.mosinsa.product.infra.repository;

import com.mosinsa.product.domain.product.Product;
import com.mosinsa.product.domain.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId>, CustomProductRepository {


}

