package com.mosinsa.product.infra.repository;

import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomProductRepositoryImplTest {

	@Autowired
	ProductRepository repository;

	@Test
	void findByCategory() {

		SearchCondition searchCondition = new SearchCondition("categoryId1");
		Page<ProductQueryDto> byCondition = repository.findByCondition(searchCondition, PageRequest.of(0, 3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);

		List<ProductQueryDto> content = byCondition.getContent();

		int idx = 0;
		List<String> answers = List.of("productId4", "productId1", "productId5");
		for (ProductQueryDto productQueryDto : content) {
			assertThat(productQueryDto.getProductId()).isEqualTo(answers.get(idx++));
		}

	}

	@Test
	void findProductsWithNoCategoryId() {

		SearchCondition searchCondition = new SearchCondition(null);
		Page<ProductQueryDto> byCondition = repository.findByCondition(searchCondition, PageRequest.of(0, 3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);

		List<ProductQueryDto> content = byCondition.getContent();

		int idx = 0;
		List<String> answers = List.of("productId4", "productId3", "productId2");
		for (ProductQueryDto productQueryDto : content) {
			assertThat(productQueryDto.getProductId()).isEqualTo(answers.get(idx++));
		}

	}

	@Test
	void findMyProducts() {
		Page<ProductQueryDto> myProducts = repository.findMyProducts("memberId2", PageRequest.of(0, 3));
		List<ProductQueryDto> content = myProducts.getContent();
		assertThat(content).hasSize(2);

		int idx = 0;
		List<String> answers = List.of("productId2", "productId1");
		for (ProductQueryDto productQueryDto : content) {
			assertThat(productQueryDto.getProductId()).isEqualTo(answers.get(idx++));
		}
	}

	@Test
	void findMyProductsWithNoMemberId() {
		PageRequest of = PageRequest.of(0, 3);
		assertThrows(ProductException.class, () -> repository.findMyProducts(null, of));
	}


}