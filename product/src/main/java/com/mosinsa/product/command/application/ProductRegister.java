package com.mosinsa.product.command.application;

import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.ui.request.CreateProductRequest;

public interface ProductRegister {
	ProductId register(CreateProductRequest request);
}
