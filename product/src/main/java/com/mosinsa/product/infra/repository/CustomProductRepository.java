package com.mosinsa.product.infra.repository;

import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.ui.request.SearchCondition;

import java.util.List;

public interface CustomProductRepository {

	List<ProductDto> findByCondition(SearchCondition condition);

}