package com.mosinsa.product.ui;

import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequests;

import java.util.List;

public class ProductServiceStub implements ProductService {
	private static boolean called = false;


	@Override
	public void orderProduct(String customerId, OrderProductRequests orderProductRequests) {

	}

	@Override
    public void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests) {
        if (orderId.equals("fail")) {
            throw new IllegalStateException();
        }
		called=true;
    }

	public boolean isCalled() {
		return called;
	}
}
