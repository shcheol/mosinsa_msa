package com.mosinsa.product.infra.repository;

import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, ProductId>, CustomProductRepository {


	@Query(value = "SELECT GET_LOCK(:key, :timeout)", nativeQuery = true)
	void getLock(@Param(value = "key") String key, @Param(value = "timeout") int timeout);

	@Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
	void unlock(@Param(value = "key") String key);

	@Query(value = "select p from Product p inner join fetch p.stock inner join fetch p.category where p.id = :productId")
	Optional<Product> findProductDetailById(@Param(value = "productId") ProductId productId);
	
}

