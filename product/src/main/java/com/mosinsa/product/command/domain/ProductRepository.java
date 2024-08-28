package com.mosinsa.product.command.domain;

import com.mosinsa.product.infra.jpa.CustomProductRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends Repository<Product, ProductId>, CustomProductRepository {


	Product save(Product product);

	Optional<Product> findById(ProductId productId);

	@Query(value = "SELECT GET_LOCK(:key, :timeout)", nativeQuery = true)
	void getLock(@Param(value = "key") String key, @Param(value = "timeout") int timeout);

	@Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
	void unlock(@Param(value = "key") String key);

	@Query(value = "select p from Product p inner join fetch p.stock inner join fetch p.category where p.id = :productId")
	Optional<Product> findProductDetailById(@Param(value = "productId") ProductId productId);

}

