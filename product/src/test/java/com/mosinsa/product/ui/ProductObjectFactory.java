package com.mosinsa.product.ui;

import com.mosinsa.category.Category;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@TestConfiguration
public class ProductObjectFactory {

	@Bean
	public ProductService productService(){
		return new ProductService() {
			@Override
			public ProductDetailDto createProduct(CreateProductRequest request) {
				return new ProductDetailDto(Product.create("",1000, Category.of("category"),1));
			}

			@Override
			public void orderProduct(String customerId, String orderId, List<OrderProductRequest> orderProducts) {

			}

			@Override
			public void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests) {

			}
		};
	}
}
