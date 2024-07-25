package com.mosinsa.product.command.application;

import com.mosinsa.category.Category;
import com.mosinsa.category.CategoryDto;
import com.mosinsa.category.CategoryService;
import com.mosinsa.category.CreateCategoryRequest;
import com.mosinsa.common.ex.CategoryError;
import com.mosinsa.common.ex.CategoryException;

import java.util.List;

public class CategoryServiceStub implements CategoryService {
    @Override
    public CategoryDto createCategory(CreateCategoryRequest request) {
        return null;
    }

    @Override
    public Category getCategory(String categoryId) {
        if (categoryId.equals("notFound")){
            throw new CategoryException(CategoryError.NOT_FOUNT_CATEGORY);
        }
        return Category.of("test");
    }

    @Override
    public List<CategoryDto> getCategoryList() {
        return null;
    }
}
