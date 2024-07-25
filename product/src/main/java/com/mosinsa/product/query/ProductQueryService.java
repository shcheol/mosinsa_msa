package com.mosinsa.product.query;

import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryService {
	ProductDetailDto getProductById(String productId);

	Page<ProductQueryDto> findProductsByCondition(SearchCondition condition, Pageable pageable);

}
