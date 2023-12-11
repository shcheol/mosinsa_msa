package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.CategoryDto;
import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.ui.request.CreateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:test-init.sql")
class CategoryServiceImplTest {

	@Autowired
	CategoryService service;

	@Test
	void createCategory() {
		assertThat(service.getCategoryList()).hasSize(3);
		CreateCategoryRequest request = new CreateCategoryRequest("신발");
		CategoryDto category = service.createCategory(request);
		assertThat(service.getCategoryList()).hasSize(4);
	}

	@Test
	void getCategoryList() {


		List<String> strings = service.getCategoryList().stream().map(CategoryDto::name).toList();

		assertThat(strings).hasSize(3);
		assertThat(strings).contains("상의","하의","아우터");

	}
}