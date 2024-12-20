package com.mosinsa.category.application;

import com.mosinsa.category.application.CategoryDto;
import com.mosinsa.category.application.CategoryService;
import com.mosinsa.category.domain.Category;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Set;

@TestConfiguration
public class CategoryObjectFactory {

	@Bean
	@Primary
	public CategoryService categoryService(){
		return new CategoryService() {
			@Override
			public void register(String name, String parentName) {
			}

			@Override
			public Category getCategory(String categoryId) {
				return null;
			}

			@Override
			public List<CategoryDto> getAllCategoriesFromRoot() {
				return List.of(new CategoryDto("testId","testName",List.of(new CategoryDto("id", "child",List.of()))));
			}

			@Override
			public CategoryDto getCategorySetFromParent(String rootId) {
				return null;
			}

			@Override
			public Set<String> getSubIds(String categoryId) {
				return null;
			}


		};
	}
}
