package com.mosinsa.category;

import java.util.List;

public interface CategoryService {

	void register(String name, String parentName);

	Category getCategory(String categoryId);

	List<CategoryDto> getAllCategoriesFromRoot();

	CategoryDto getCategorySetFromParent(String rootId);
}
