package com.mosinsa.product.command.application;

import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;

import java.util.List;

public interface ProductService {
	ProductId createProduct(CreateProductRequest request);

	void orderProduct(String customerId, String orderId, List<OrderProductRequest> orderProducts);

	void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests);
}
