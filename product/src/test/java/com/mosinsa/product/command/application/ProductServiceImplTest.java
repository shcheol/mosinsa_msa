package com.mosinsa.product.command.application;

import com.mosinsa.common.ex.CategoryException;
import com.mosinsa.product.command.domain.StockStatus;
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
class ProductServiceImplTest {

	@Autowired
	ProductQueryService productQueryService;
	@Autowired
	ProductServiceImpl productServiceImpl;
	@Autowired
	StockService stockService;

	@Test
	@DisplayName(value = "상품등록")
	void registerProduct() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 10);
		ProductDetailDto product = productServiceImpl.createProduct(createProductRequest);
		assertThat(product.getName()).isEqualTo("product");
		assertThat(product.getPrice()).isEqualTo(3000);
		assertThat(product.getTotalStock()).isEqualTo(10);
		assertThat(stockService.currentStock(product.getProductId())).isEqualTo(10);
	}

	@Test
	@DisplayName(value = "상품등록실패 - 카테고리 존재x")
	void registerProductNotExistsCategory() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1xxx", 10);
		assertThrows(CategoryException.class, () -> productServiceImpl.createProduct(createProductRequest));
	}

	@Test
	@DisplayName(value = "재고 범위의 수량을 주문하면 재고가 줄어든다.")
	void orderProduct() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 10);
		ProductDetailDto product = productServiceImpl.createProduct(createProductRequest);

		String productId = product.getProductId();
		long beforeStock = stockService.currentStock(productId);
		assertThat(beforeStock).isEqualTo(10);

		OrderProductRequest request = new OrderProductRequest(productId, 3);
		productServiceImpl.orderProduct("customerId1", "orderId1", List.of(request));

		long afterStock = stockService.currentStock(productId);
		assertThat(afterStock).isEqualTo(7);

		OrderProductRequest request2 = new OrderProductRequest(productId, 7);
		productServiceImpl.orderProduct("customerId1", "orderId2", List.of(request2));

		long afterStock2 = stockService.currentStock(productId);
		assertThat(afterStock2).isZero();
	}

	@Test
	@DisplayName(value = "재고가 0이되면 상품 상태가 SOLD_OUT으로 변경된다.")
	void orderProductSoldOut() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 10);
		ProductDetailDto product = productServiceImpl.createProduct(createProductRequest);
		assertThat(product.getStockStatus()).isEqualTo(StockStatus.ON);
		String productId = product.getProductId();
		long beforeStock = stockService.currentStock(productId);
		assertThat(beforeStock).isEqualTo(10);

		OrderProductRequest request = new OrderProductRequest(productId, 10);
		productServiceImpl.orderProduct("customerId1", "orderId1", List.of(request));

		long afterStock = stockService.currentStock(productId);
		assertThat(afterStock).isZero();

		assertThat(productQueryService.getProductById(productId).getStockStatus()).isEqualTo(StockStatus.SOLD_OUT);
	}

	@Test
	@DisplayName(value = "재고보다 많은 수량을 주문하면 예외가 발생하고 재고는 줄어들지 않는다.")
	void orderProduct_Ex() {

		String productId = "productId1";
		stockService.setStock(productId, 10);
		long beforeStock = stockService.currentStock(productId);
		assertThat(beforeStock).isEqualTo(10);
		List<OrderProductRequest> products = List.of(new OrderProductRequest(productId, 11));
		assertThrows(RuntimeException.class,
				() -> productServiceImpl.orderProduct("customerId1", "orderId1", products));
		long afterStock = stockService.currentStock(productId);
		assertThat(afterStock).isEqualTo(10);
	}

	@Test
	@DisplayName("재고감소 - 동시요청")
	void orderProductConcurrency() throws InterruptedException {

		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 30);
		CreateProductRequest createProductRequest2 = new CreateProductRequest("product", 3000, "categoryId1", 30);
		ProductDetailDto product = productServiceImpl.createProduct(createProductRequest);
		ProductDetailDto product2 = productServiceImpl.createProduct(createProductRequest2);
		String productId = product.getProductId();
		long beforeStock = stockService.currentStock(productId);
		assertThat(beforeStock).isEqualTo(30);
		String productId2 = product2.getProductId();
		long beforeStock2 = stockService.currentStock(productId2);
		assertThat(beforeStock2).isEqualTo(30);

		int size = 10;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					productServiceImpl.orderProduct("customerId1", "orderId1", List.of(
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
	@DisplayName(value = "주문 취소하면 재고가 올라간다.")
	void cancelOrderProduct() {
		String productId = "productId2";
		stockService.setStock(productId, 20);

		CancelOrderProductRequest request = new CancelOrderProductRequest(productId, 3);
		productServiceImpl.cancelOrderProduct("customerId1", "orderId1", List.of(request));

		long afterStock = stockService.currentStock(productId);
		assertThat(afterStock).isEqualTo(23);

	}

	@Test
	@DisplayName(value = "잔여 수량이 0인 상품을 주문 취소하면 재고가 올라가고 재고 상태 ON으로 변경된다.")
	void cancelOrderProductWhenStockIsZero() {
		CreateProductRequest createProductRequest = new CreateProductRequest("product", 3000, "categoryId1", 10);
		ProductDetailDto product = productServiceImpl.createProduct(createProductRequest);
		String productId = product.getProductId();

		OrderProductRequest request = new OrderProductRequest(productId, 10);
		productServiceImpl.orderProduct("customerId1", "orderId1", List.of(request));

		assertThat(stockService.currentStock(productId)).isZero();
		assertThat(productQueryService.getProductById(productId).getStockStatus()).isEqualTo(StockStatus.SOLD_OUT);

		productServiceImpl.cancelOrderProduct("customerId1","orderId1", List.of(new CancelOrderProductRequest(productId, 10)));
		assertThat(stockService.currentStock(productId)).isEqualTo(10);
		assertThat(productQueryService.getProductById(productId).getStockStatus()).isEqualTo(StockStatus.ON);
	}


}