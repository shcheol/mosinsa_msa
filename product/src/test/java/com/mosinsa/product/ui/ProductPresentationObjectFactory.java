package com.mosinsa.product.ui;

import com.mosinsa.category.domain.Category;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.query.ProductDetailDto;
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
			public ProductDetailDto getProductById(String productId) {
				return new ProductDetailDto(Product.create("test", 1000, Category.of("category"), 10), 5);
			}

			@Override
			public Page<ProductQueryDto> findProductsByCondition(SearchCondition condition, Pageable pageable) {
				return new PageImpl<>(
						List.of(new ProductQueryDto("", "", 1000),
								new ProductQueryDto("", "", 1000))
				);
			}

			@Override
			public Page<ProductQueryDto> findMyProducts(String memberId, Pageable pageable) {
				return new PageImpl<>(
						List.of(
								new ProductQueryDto("", "", 1000),
								new ProductQueryDto("", "", 1000)
						)
				);
			}
		};
	}
}
