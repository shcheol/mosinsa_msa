package com.mosinsa.product.command.application;

import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequests;

import java.util.List;

public interface ProductService {

	void orderProduct(String customerId, OrderProductRequests orderProductRequests);

	void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests);
}
