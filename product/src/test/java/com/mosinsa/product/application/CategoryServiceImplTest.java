package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.CategoryDto;
import com.mosinsa.common.ex.CategoryException;
import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.ui.request.CreateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Test
	void getCategoryEx(){
		assertThrows(CategoryException.class, () -> service.getCategory("categoryId1xxxx"));
	}

	@Test
	void getCategory(){

		Category category1 = service.getCategory("categoryId1");
		Category category2 = service.getCategory("categoryId1");
		assertThat(category1).isEqualTo(category2).hasSameHashCodeAs(category2);
	}

}