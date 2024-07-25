package com.mosinsa.product.query;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ProductQueryServiceImplTest {
	@Autowired
	ProductQueryServiceImpl productQueryServiceImpl;

	@Test
	void getProductEx() {
		String productId = "productId1xxx";

		assertThrows(ProductException.class, () ->
				productQueryServiceImpl.getProductById(productId));
	}

	@Test
	void findByCondition(){
		SearchCondition searchCondition = new SearchCondition("categoryId1");
		Page<ProductQueryDto> byCondition = productQueryServiceImpl.findProductsByCondition(searchCondition, PageRequest.of(0,3));
		int size = byCondition.getContent().size();
		assertThat(size).isEqualTo(3);

		List<ProductQueryDto> content = byCondition.getContent();

		int idx=0;
		List<String> answers = List.of("productId4", "productId1", "productId5");
		for (ProductQueryDto productQueryDto : content) {
			assertThat(productQueryDto.getProductId()).isEqualTo(answers.get(idx++));
		}
	}

}