package com.mosinsa.product.infra.jpa;

import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.domain.ProductRepository;
import com.mosinsa.product.query.dto.ProductSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomProductRepositoryImplTest {

	@Autowired
	ProductRepository repository;

	@Test
	void findByCategory() {

		Page<ProductSummary> byCondition = repository.findByCondition(new CategorySearchCondition(Set.of("categoryId1")), PageRequest.of(0, 3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);

		List<ProductSummary> content = byCondition.getContent();

		int idx = 0;
		List<String> answers = List.of("productId4", "productId1", "productId5");
		for (ProductSummary productSummary : content) {
			assertThat(productSummary.getProductId()).isEqualTo(answers.get(idx++));
		}

	}

	@Test
	void findProductsWithNoCategoryId() {

		Page<ProductSummary> byCondition = repository.findByCondition(new CategorySearchCondition(null), PageRequest.of(0, 3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);

		List<ProductSummary> content = byCondition.getContent();

		int idx = 0;
		List<String> answers = List.of("productId4", "productId3", "productId2");
		for (ProductSummary productSummary : content) {
			assertThat(productSummary.getProductId()).isEqualTo(answers.get(idx++));
		}

	}

	@Test
	void findMyProducts() {
		Page<ProductSummary> myProducts = repository.findMyProducts("memberId2", PageRequest.of(0, 3));
		List<ProductSummary> content = myProducts.getContent();
		assertThat(content).hasSize(2);

		int idx = 0;
		List<String> answers = List.of("productId2", "productId1");
		for (ProductSummary productSummary : content) {
			assertThat(productSummary.getProductId()).isEqualTo(answers.get(idx++));
		}
	}

	@Test
	void findMyProductsWithNoMemberId() {
		PageRequest of = PageRequest.of(0, 3);
		assertThrows(ProductException.class, () -> repository.findMyProducts(null, of));
	}


}