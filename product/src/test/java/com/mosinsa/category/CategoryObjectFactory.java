package com.mosinsa.category;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

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
			public List<CategoryDto> getCategoryList() {
				return List.of(new CategoryDto("testId","testName",List.of(new CategoryDto("id", "child",List.of()))));
			}
		};
	}
}
