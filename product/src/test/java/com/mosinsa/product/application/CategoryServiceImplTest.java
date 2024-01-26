package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.CategoryDto;
import com.mosinsa.product.ui.request.CreateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CategoryServiceImplTest {

	@Autowired
	CategoryService service;

	@Test
	void createCategory() {
		assertThat(service.getCategoryList()).hasSize(9);
		CreateCategoryRequest request = new CreateCategoryRequest("신발");
		service.createCategory(request);
		assertThat(service.getCategoryList()).hasSize(10);
	}

	@Test
	void getCategoryList() {
		List<String> strings = service.getCategoryList().stream().map(CategoryDto::name).toList();
		assertThat(strings).contains("상의", "바지", "아우터", "신발", "가방", "양말", "원피스", "치마", "모자");
	}
}