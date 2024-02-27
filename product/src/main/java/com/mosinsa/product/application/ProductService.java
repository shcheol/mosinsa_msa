package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDetailDto;
import com.mosinsa.product.application.dto.ProductQueryDto;
import com.mosinsa.product.ui.request.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

	ProductDetailDto createProduct(CreateProductRequest createProductRequest);

	ProductDetailDto getProductById(String productId);

	List<ProductDetailDto> findMyLikesProducts(String customerId);

	Page<ProductQueryDto> findProductsByCondition(SearchCondition condition, Pageable pageable);

	void orderProduct(List<OrderProductRequest> requests);

	void cancelOrderProduct(List<CancelOrderProductRequest> requests);

	void likes(LikesProductRequest request);

}
