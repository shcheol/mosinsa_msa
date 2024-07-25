package com.mosinsa.category;

import com.mosinsa.common.ex.CategoryError;
import com.mosinsa.common.ex.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;

	@Override
	@Transactional
	public CategoryDto createCategory(CreateCategoryRequest request) {
		return CategoryDto.convert(repository.save(Category.of(request.name())));
	}

	@Override
	public Category getCategory(String categoryId) {
		return repository.findById(CategoryId.of(categoryId))
				.orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUNT_CATEGORY));
	}

	@Override
	public List<CategoryDto> getCategoryList() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "name"))
				.stream().map(CategoryDto::convert).toList();
	}
}
