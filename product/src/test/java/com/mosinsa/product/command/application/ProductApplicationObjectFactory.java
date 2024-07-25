package com.mosinsa.product.command.application;

import com.mosinsa.category.Category;
import com.mosinsa.category.CategoryDto;
import com.mosinsa.category.CategoryService;
import com.mosinsa.category.CreateCategoryRequest;
import com.mosinsa.product.infra.redis.StockOperand;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@TestConfiguration
public class ProductApplicationObjectFactory {

	@Bean
	public CategoryService categoryService(){
		return new CategoryService() {
			@Override
			public CategoryDto createCategory(CreateCategoryRequest request) {
				return null;
			}

			@Override
			public Category getCategory(String categoryId) {
				return Category.of("test");
			}

			@Override
			public List<CategoryDto> getCategoryList() {
				return null;
			}
		};
	}

	@Bean
	public StockPort stockPort(){
		return new StockPort(){

			@Override
			public long currentStock(String key) {
				return 0;
			}

			@Override
			public void setStock(String key, long stock) {

			}

			@Override
			public StockResult tryDecrease(String customerId, String orderId, List<StockOperand> stockOperands) {
				return null;
			}

			@Override
			public StockResult tryIncrease(String customerId, String orderId, List<StockOperand> stockOperands) {
				return null;
			}
		};
	}
}
