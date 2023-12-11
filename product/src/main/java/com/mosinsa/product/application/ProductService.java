package com.mosinsa.product.application;

import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.ui.request.DecreaseStockRequest;
import com.mosinsa.product.ui.request.IncreaseStockRequest;
import com.mosinsa.product.ui.request.LikesProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

	ProductDto createProduct(CreateProductRequest createProductRequest);

	ProductDto getProductById(String productId);

	Page<ProductDto> getAllProducts(Pageable pageable);

	void likes(LikesProductRequest request);

	void increaseStock(IncreaseStockRequest stock);

	void decreaseStock(DecreaseStockRequest stock);

}
