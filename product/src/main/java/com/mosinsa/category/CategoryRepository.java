package com.mosinsa.category;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends Repository<Category, CategoryId> {

	Category save(Category category);

	Optional<Category> findById(CategoryId categoryId);

	@Query(value = "select c from Category c left join fetch c.children where c.id = :categoryId")
	Optional<Category> findDetailsById(@Param("categoryId") CategoryId categoryId);

	@Query(value = "select c from Category c where c.name = :name")
	Optional<Category> findByName(@Param("name") String name);

	@Query(value = "select c from Category c left join fetch c.children where c.parent is null")
	List<Category> findCategoriesFromRoot(Sort sort);

	@Query(value = "select c from Category c join fetch c.children where c.id = :parentId")
	Category findCategoriesFromParent(@Param("parentId") CategoryId parentId);
}
