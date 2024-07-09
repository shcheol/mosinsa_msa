package com.mosinsa.product.command.application;

import com.mosinsa.common.ex.CategoryException;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.query.ProductQueryService;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class ProductServiceTest {

	@Autowired
	ProductQueryService productQueryService;
	@Autowired
	ProductService productService;

	@Autowired
	StockService stockService;

	@Test
	void registerProduct() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 10);
		ProductDetailDto product = productService.createProduct(createProductRequest);
		assertThat(product.getName()).isEqualTo("product");
		assertThat(product.getPrice()).isEqualTo(3000);
		assertThat(product.getStock()).isEqualTo(10);
	}

	@Test
	void registerProductNotExistsCategory() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1xxx", 10);
		assertThrows(CategoryException.class, () -> productService.createProduct(createProductRequest));
	}

	@Test
	void orderProduct() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 10);
		ProductDetailDto product = productService.createProduct(createProductRequest);
		String productId = product.getProductId();
		long beforeStock = productQueryService.getProductById(productId).getStock();
		assertThat(beforeStock).isEqualTo(10);

		OrderProductRequest request = new OrderProductRequest(productId, 3);
		productService.orderProduct("customerId1", "orderId1", List.of(request));

		long afterStock = productQueryService.getProductById(productId).getStock();
		assertThat(afterStock).isEqualTo(7);

	}

	@Test
	void orderProduct_Ex() {

		long beforeStock = productQueryService.getProductById("productId1").getStock();
		assertThat(beforeStock).isEqualTo(10);
		List<OrderProductRequest> products = List.of(new OrderProductRequest("productId1", 11));
		assertThrows(RuntimeException.class,
				() -> productService.orderProduct("customerId1", "orderId1", products));
		long afterStock = productQueryService.getProductById("productId1").getStock();
		assertThat(afterStock).isEqualTo(10);
	}

	@Test
	@DisplayName("재고감소 - 동시요청")
	void orderProductConcurrency() throws InterruptedException {

		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 30);
		CreateProductRequest createProductRequest2 = new CreateProductRequest("product", 3000, "categoryId1", 30);
		ProductDetailDto product = productService.createProduct(createProductRequest);
		ProductDetailDto product2 = productService.createProduct(createProductRequest2);
		String productId =product.getProductId();
		long beforeStock = stockService.currentStock(productId);
		assertThat(beforeStock).isEqualTo(30);
		String productId2 =product2.getProductId();
		long beforeStock2 = stockService.currentStock(productId2);
		assertThat(beforeStock2).isEqualTo(30);

		int size = 10;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					productService.orderProduct("customerId1", "orderId1", List.of(
							new OrderProductRequest(productId, 1),
							new OrderProductRequest(productId2, 1)
							));
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		es.shutdown();

		long afterStock = stockService.currentStock(productId);
		assertThat(afterStock).isEqualTo(20);
		long afterStock2 = stockService.currentStock(productId2);
		assertThat(afterStock2).isEqualTo(20);
	}

	@Test
	void cancelOrderProduct() {
		long beforeStock = productQueryService.getProductById("productId2").getStock();
		assertThat(beforeStock).isEqualTo(20);

		CancelOrderProductRequest request = new CancelOrderProductRequest("productId2", 3);
		productService.cancelOrderProduct("customerId1", "orderId1", List.of(request));

		long afterStock = productQueryService.getProductById("productId2").getStock();
		assertThat(afterStock).isEqualTo(23);

	}


}