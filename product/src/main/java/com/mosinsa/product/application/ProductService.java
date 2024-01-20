package com.mosinsa.product.application;

import com.mosinsa.product.ui.request.*;
import com.mosinsa.product.application.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

	ProductDto createProduct(CreateProductRequest createProductRequest);

	ProductDto getProductById(String productId);

	Page<ProductDto> getAllProducts(Pageable pageable);

	List<ProductDto> findMyLikesProducts(String customerId);

	Page<ProductDto> findProductsByCondition(SearchCondition condition, Pageable pageable);

	void orderProduct(List<OrderProductRequest> requests);

	void cancelOrderProduct(List<CancelOrderProductRequest> requests);

	void likes(LikesProductRequest request);

}
