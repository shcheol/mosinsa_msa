package com.mosinsa.category.application;

import com.mosinsa.category.domain.Category;
import com.mosinsa.category.domain.CategoryId;
import com.mosinsa.category.domain.CategoryRepository;
import com.mosinsa.common.ex.CategoryError;
import com.mosinsa.common.ex.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;

	@Override
	@Transactional
	public void register(String name, String parentName) {
		Category parent = null;
		if (StringUtils.hasText(parentName)){
			parent = repository.findByName(parentName).orElseThrow();
		}
		repository.save(Category.of(name, parent));
	}

	@Override
	public Category getCategory(String categoryId) {
		return repository.findDetailsById(CategoryId.of(categoryId))
				.orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUNT_CATEGORY));
	}

	@Override
	public List<CategoryDto> getAllCategoriesFromRoot() {

		return repository.findCategoriesFromRoot(Sort.by(Sort.Direction.ASC, "name"))
				.stream()
				.map(CategoryDto::convert)
				.toList();
	}

	@Override
	public CategoryDto getCategorySetFromParent(String rootId) {
		return CategoryDto.convert(repository.findCategoriesFromParent(CategoryId.of(rootId)));
	}

}