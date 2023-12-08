package com.mosinsa.product.application;

import com.mosinsa.product.ui.request.ProductAddRequest;
import com.mosinsa.product.ui.request.ProductUpdateRequest;
import com.mosinsa.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

	ProductDto addProduct(ProductAddRequest productAddRequest);

	void updateProduct(String productId, ProductUpdateRequest productUpdateRequest);

	ProductDto findProductById(String productId);

	Page<ProductDto> findAllProducts(Pageable pageable);
}
