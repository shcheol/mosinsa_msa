package com.mosinsa.category.application;

import com.mosinsa.category.application.CategoryDto;
import com.mosinsa.category.application.CategoryServiceImpl;
import com.mosinsa.common.ex.CategoryException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CategoryServiceImplTest {

	@Autowired
	CategoryServiceImpl service;

	@Test
	void register() {
		int size = service.getAllCategoriesFromRoot().size();
		service.register("new신발", null);
		assertThat(service.getAllCategoriesFromRoot()).hasSize(size + 1);
	}

	@Test
	void registerHasParentCategory() {
		String parentId = "categoryId4";
		String parentName = "신발";
		int size = service.getCategory(parentId).getChildren().size();
		service.register("new신발", parentName);
		assertThat(service.getCategory(parentId).getChildren()).hasSize(size + 1);
	}

	@Test
	void registerDuplicateNameThrowsException() {
		service.register("new신발", null);
		assertThrows(RuntimeException.class, () -> service.register("new신발", null));
	}

	@Test
	void registerInvalidParentNameThrowsException() {
		String parentName = "신발xxxxx";

		assertThrows(RuntimeException.class, () -> service.register("new신발", parentName));
	}

	@Test
	void getRepresentCategoryList() {
		List<CategoryDto> categoryList = service.getAllCategoriesFromRoot();
		List<String> strings = categoryList.stream().map(CategoryDto::name).toList();
		assertThat(strings).containsExactly("가방", "바지", "상의", "소품", "신발", "아우터");
		System.out.println(categoryList);
		System.out.println(categoryList.size());
	}

	@Test
	void getCategoryEx() {
		assertThrows(CategoryException.class, () -> service.getCategory("categoryId1xxxx"));
	}

	@Test
	void getSubCategoryIds() {
		Set<String> root1 = service.getSubIds("categoryId1");
		assertThat(root1).containsExactlyInAnyOrder("categoryId1", "categoryId7", "categoryId8", "categoryId9", "categoryId10");

		Set<String> root2 = service.getSubIds("categoryId4");
		assertThat(root2).containsExactlyInAnyOrder("categoryId19", "categoryId4", "categoryId41", "categoryId31", "categoryId20", "categoryId42", "categoryId40", "categoryId34", "categoryId35", "categoryId32", "categoryId21", "categoryId33", "categoryId22", "categoryId38", "categoryId39", "categoryId36", "categoryId37");

		Set<String> mid1 = service.getSubIds("categoryId10");
		assertThat(mid1).containsExactlyInAnyOrder("categoryId10");

		Set<String> mid2 = service.getSubIds("categoryId19");
		assertThat(mid2).containsExactlyInAnyOrder("categoryId19", "categoryId31", "categoryId32", "categoryId33");
	}

	@Test
	void getSubCategoryIdsEx() {
		assertThrows(CategoryException.class, ()->service.getSubIds("categoryId1xxxxx"));
	}

}