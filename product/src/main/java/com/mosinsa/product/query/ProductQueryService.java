package com.mosinsa.product.query;

import com.mosinsa.product.query.dto.ProductDetails;
import com.mosinsa.product.query.dto.ProductSummary;
import com.mosinsa.product.ui.request.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryService {
	ProductDetails getProductById(String productId);

	Page<ProductSummary> findProductsByCondition(SearchCondition condition, Pageable pageable);

	Page<ProductSummary> findMyProducts(String memberId, Pageable pageable);
}
