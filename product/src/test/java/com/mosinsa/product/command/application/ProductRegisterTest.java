package com.mosinsa.product.command.application;

import com.mosinsa.common.ex.CategoryException;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.ui.request.CreateProductRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ProductRegisterTest {
	@Autowired
	ProductRegister productRegister;
	@Test
	@DisplayName(value = "상품등록")
	void registerProduct() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", "", 1000, "categoryId1", List.of());
		ProductId id = productRegister.register(createProductRequest);
		Assertions.assertThat(id).isNotNull();
	}

	@Test
	@DisplayName(value = "상품등록실패 - 카테고리 존재x")
	void registerProductNotExistsCategory() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", "", 1000, "notFound", List.of());
		assertThrows(CategoryException.class, () -> productRegister.register(createProductRequest));
	}

}