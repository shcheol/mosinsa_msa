package com.mosinsa.category;

import com.mosinsa.common.ex.CategoryError;
import com.mosinsa.common.ex.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository repository;

	@Transactional
	public CategoryDto createCategory(CreateCategoryRequest request) {
		return CategoryDto.convert(
				repository.save(Category.of(request.name()))
		);
	}

	public Category getCategory(String categoryId) {
		return repository.findById(CategoryId.of(categoryId))
				.orElseThrow(() -> new CategoryException(CategoryError.NOT_FOUNT_CATEGORY));
	}

	public List<CategoryDto> getCategoryList() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "name"))
				.stream().map(CategoryDto::convert).toList();
	}
}
