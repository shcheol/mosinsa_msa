package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.ui.request.DecreaseStockRequest;
import com.mosinsa.product.ui.request.IncreaseStockRequest;
import com.mosinsa.product.ui.request.LikesProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:test-init.sql")
class ProductServiceImplTest {

	@Autowired
	ProductService productService;


	@Test
	void getProductEx() {
		String productId = "productId1xxx";

		assertThrows(ProductException.class, () ->
				productService.getProductById(productId));
	}

	@Test
	void getAllProducts(){
		Page<ProductDto> allProducts = productService.getAllProducts(PageRequest.of(0, 20));
		assertThat(allProducts.getTotalElements()).isEqualTo(3);
	}

	@Test
	void increaseStock(){

		long beforeStock = productService.getProductById("productId1").getStock();
		assertThat(beforeStock).isEqualTo(10);

		IncreaseStockRequest request = new IncreaseStockRequest("productId1", 3);
		productService.increaseStock(request);

		long afterStock = productService.getProductById("productId1").getStock();
		assertThat(afterStock).isEqualTo(13);

	}

	@Test
	void decreaseStock(){

		long beforeStock = productService.getProductById("productId2").getStock();
		assertThat(beforeStock).isEqualTo(20);

		DecreaseStockRequest request = new DecreaseStockRequest("productId2", 3);
		productService.decreaseStock(request);

		long afterStock = productService.getProductById("productId2").getStock();
		assertThat(afterStock).isEqualTo(17);

	}

	@Test
	void likes() {
		LikesProductRequest user1 = new LikesProductRequest("productId1", "memberId1");
		LikesProductRequest user2 = new LikesProductRequest("productId1", "memberId2");

		productService.likes(user1);
		assertThat(
				productService.getProductById(
						user1.productId()).getLikes())
				.isEqualTo(1);

		productService.likes(user2);
		assertThat(
				productService.getProductById(
						user1.productId()).getLikes())
				.isEqualTo(2);

		productService.likes(user2);
		assertThat(
				productService.getProductById(
						user1.productId()).getLikes())
				.isEqualTo(1);

		productService.likes(user1);
		assertThat(
				productService.getProductById(
						user1.productId()).getLikes())
				.isZero();
	}

}