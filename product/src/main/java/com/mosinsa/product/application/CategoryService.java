package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.CategoryDto;
import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.ui.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {

	CategoryDto createCategory(CreateCategoryRequest createCategoryRequest);

	Category getCategory(String categoryId);

	List<CategoryDto> getCategoryList();
}
