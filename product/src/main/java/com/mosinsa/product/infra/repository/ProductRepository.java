package com.mosinsa.product.infra.repository;

import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.domain.product.Product;
import com.mosinsa.product.domain.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, ProductId>, CustomProductRepository {


	@Query(value = "SELECT GET_LOCK(:key, :timeout)", nativeQuery = true)
	void getLock(@Param(value = "key")String key, @Param(value = "timeout")int timeout);

	@Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
	void unlock(@Param(value = "key") String key);

	@Query(value = "select new com.mosinsa.product.application.dto.ProductDto(p) from Product p where p")
	List<ProductDto> findLikesProduct(String likesMember);
}

