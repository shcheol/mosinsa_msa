package com.mosinsa.category;

import java.util.List;

public interface CategoryService {

	CategoryDto createCategory(CreateCategoryRequest request);

	Category getCategory(String categoryId);

	List<CategoryDto> getCategoryList();
}
