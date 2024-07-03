package com.mosinsa.product.infra.repository;

import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {

	Page<ProductQueryDto> findByCondition(SearchCondition condition, Pageable pageable);

}