package com.mosinsa.category.application;

import com.mosinsa.category.domain.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

	void register(String name, String parentName);

	Category getCategory(String categoryId);

	List<CategoryDto> getAllCategoriesFromRoot();

	CategoryDto getCategorySetFromParent(String rootId);

	Set<String> getSubIds(String categoryId);
}
