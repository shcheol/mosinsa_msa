package com.mosinsa.product.ui;

import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.application.ProductRegister;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.ui.request.CreateProductRequest;

public class ProductRegisterStub implements ProductRegister {
	@Override
	public ProductId register(CreateProductRequest request) {
		if (request.name().equals("error")) {
			throw new RuntimeException();
		}
		if (request.name().equals("productException4xx")) {
			throw new ProductException(ProductError.NOT_FOUNT_PRODUCT);
		}
		if (request.name().equals("productException5xx")) {
			throw new ProductException(ProductError.INTERNAL_SERVER_ERROR);
		}
		return ProductId.of("id");
	}
}
