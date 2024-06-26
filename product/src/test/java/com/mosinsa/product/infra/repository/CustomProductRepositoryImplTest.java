package com.mosinsa.product.infra.repository;

import com.mosinsa.product.application.dto.ProductQueryDto;
import com.mosinsa.product.ui.request.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomProductRepositoryImplTest {

	@Autowired
	ProductRepository repository;

	@Test
	void test(){

		SearchCondition searchCondition = new SearchCondition("categoryId1");
		Page<ProductQueryDto> byCondition = repository.findByCondition(searchCondition, PageRequest.of(0,3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);

		List<ProductQueryDto> content = byCondition.getContent();

		int idx=0;
		List<String> answers = List.of("productId4", "productId1", "productId5");
		for (ProductQueryDto productQueryDto : content) {
			assertThat(productQueryDto.getProductId()).isEqualTo(answers.get(idx++));
		}

	}

	@Test
	void testNull(){

		SearchCondition searchCondition = new SearchCondition(null);
		Page<ProductQueryDto> byCondition = repository.findByCondition(searchCondition, PageRequest.of(0,3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);

		List<ProductQueryDto> content = byCondition.getContent();

		int idx=0;
		List<String> answers = List.of("productId4", "productId3", "productId2");
		for (ProductQueryDto productQueryDto : content) {
			assertThat(productQueryDto.getProductId()).isEqualTo(answers.get(idx++));
		}

	}


}