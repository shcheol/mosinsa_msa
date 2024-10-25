package com.mosinsa.product.infra.jpa;

import com.mosinsa.product.query.dto.ProductSummary;
import com.mosinsa.product.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {

	Page<ProductSummary> findByCondition(CategorySearchCondition condition, Pageable pageable);

	Page<ProductSummary> findMyProducts(String memberId, Pageable pageable);

}