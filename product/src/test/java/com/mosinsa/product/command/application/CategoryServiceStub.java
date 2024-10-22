package com.mosinsa.product.command.application;

import com.mosinsa.category.Category;
import com.mosinsa.category.CategoryDto;
import com.mosinsa.category.CategoryService;
import com.mosinsa.common.ex.CategoryError;
import com.mosinsa.common.ex.CategoryException;

import java.util.List;

public class CategoryServiceStub implements CategoryService {

    @Override
    public void register(String name, String parentName) {
    }

    @Override
    public Category getCategory(String categoryId) {
        if (categoryId.equals("notFound")){
            throw new CategoryException(CategoryError.NOT_FOUNT_CATEGORY);
        }
        return Category.of("test",null);
    }

    @Override
    public List<CategoryDto> getCategoryList() {
        return null;
    }
}
