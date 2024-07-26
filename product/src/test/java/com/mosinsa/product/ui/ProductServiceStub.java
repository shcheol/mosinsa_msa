package com.mosinsa.product.ui;

import com.mosinsa.category.Category;
import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;

import java.util.List;

public class ProductServiceStub implements ProductService {
	private static boolean called = false;
    @Override
    public ProductDetailDto createProduct(CreateProductRequest request) {
        if (request.name().equals("error")) {
            throw new RuntimeException();
        }
        if (request.name().equals("productException4xx")) {
            throw new ProductException(ProductError.NOT_FOUNT_PRODUCT);
        }
        if (request.name().equals("productException5xx")) {
            throw new ProductException(ProductError.INTERNAL_SERVER_ERROR);
        }
        return new ProductDetailDto(Product.create(request.name(), request.price(), Category.of(request.category()), request.stock()));
    }

    @Override
    public void orderProduct(String customerId, String orderId, List<OrderProductRequest> orderProducts) {

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
