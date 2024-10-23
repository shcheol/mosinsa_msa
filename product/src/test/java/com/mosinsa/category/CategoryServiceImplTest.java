package com.mosinsa.category;

import com.mosinsa.common.ex.CategoryException;
import org.assertj.core.api.Assertions;
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
	CategoryServiceImpl service;

	@Test
	void register() {
		int size = service.getAllCategoriesFromRoot().size();
		service.register("new신발", null);
		assertThat(service.getAllCategoriesFromRoot()).hasSize(size+1);
	}
	@Test
	void registerHasParentCategory() {
		String parentId = "categoryId4";
		String parentName = "신발";
		int size = service.getCategory(parentId).getChildren().size();
		service.register("new신발", parentName);
		Assertions.assertThat(service.getCategory(parentId).getChildren()).hasSize(size+1);
	}
	@Test
	void registerDuplicateNameThrowsException() {
		service.register("new신발", null);
		assertThrows(RuntimeException.class, () ->service.register("new신발", null));
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
		assertThat(strings).containsExactly( "가방", "바지","상의", "소품", "신발", "아우터");
		System.out.println(categoryList);
		System.out.println(categoryList.size());
	}

	@Test
	void getCategoryEx(){
		assertThrows(CategoryException.class, () -> service.getCategory("categoryId1xxxx"));
	}

}