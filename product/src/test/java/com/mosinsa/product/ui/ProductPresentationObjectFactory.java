package com.mosinsa.product.ui;

import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.query.dto.ProductSummary;
import com.mosinsa.product.command.domain.StockStatus;
import com.mosinsa.product.query.dto.ProductDetails;
import com.mosinsa.product.query.ProductQueryService;
import com.mosinsa.product.ui.request.SearchCondition;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@TestConfiguration
public class ProductPresentationObjectFactory {

	@Bean
	public ProductService productService() {
		return new ProductServiceStub();
	}


	@Bean
	public ProductQueryService productQueryService() {
		return new ProductQueryService() {
			@Override
			public ProductDetails getProductById(String productId) {
				return new ProductDetails("id","test", 1000, 10, 10, StockStatus.ON);
			}

			@Override
			public Page<ProductSummary> findProductsByCondition(SearchCondition condition, Pageable pageable) {
				return new PageImpl<>(
						List.of(new ProductSummary("", "", 1000),
								new ProductSummary("", "", 1000))
				);
			}

			@Override
			public Page<ProductSummary> findMyProducts(String memberId, Pageable pageable) {
				return new PageImpl<>(
						List.of(
								new ProductSummary("", "", 1000),
								new ProductSummary("", "", 1000)
						)
				);
			}
		};
	}
}
