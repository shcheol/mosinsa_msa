package com.mosinsa.product.infra.repository;

import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.domain.category.CategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, CategoryId> {
}
