package com.mosinsa.product.service;

import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.db.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

	ProductDto addProduct(ProductAddRequest productAddRequest);

	void updateProduct(String productId, ProductUpdateRequest productUpdateRequest);

	ProductDto findProductById(String productId);

	Page<ProductDto> findAllProducts(Pageable pageable);
}
