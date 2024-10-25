package com.mosinsa.product.command.application;

import com.mosinsa.category.domain.Category;
import com.mosinsa.category.application.CategoryDto;
import com.mosinsa.category.application.CategoryService;
import com.mosinsa.common.ex.CategoryError;
import com.mosinsa.common.ex.CategoryException;

import java.util.List;
import java.util.Set;

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
    public List<CategoryDto> getAllCategoriesFromRoot() {
        return null;
    }

	@Override
	public CategoryDto getCategorySetFromParent(String rootId) {
		return null;
	}

	@Override
	public Set<String> getSubIds(String categoryId) {
		return null;
	}

}
