package com.mosinsa.product.command.application;

import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;

import java.util.List;

public interface ProductService {
	ProductDetailDto createProduct(CreateProductRequest request);

	void orderProduct(String customerId, String orderId, List<OrderProductRequest> orderProducts);

	void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests);
}
