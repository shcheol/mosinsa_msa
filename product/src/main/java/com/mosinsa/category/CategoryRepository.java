package com.mosinsa.category;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends Repository<Category, CategoryId> {

	Category save(Category category);

	Optional<Category> findById(CategoryId categoryId);

	List<Category> findAll(Sort sort);
}
