package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDetailDto;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.LikesProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;
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
	ProductCommandService productCommandService;


	@Test
	void getProductEx() {
		String productId = "productId1xxx";

		assertThrows(ProductException.class, () ->
				productQueryService.getProductById(productId));
	}

	@Test
	void orderProduct() {

		long beforeStock = productQueryService.getProductById("productId1").getStock();
		assertThat(beforeStock).isEqualTo(10);

		OrderProductRequest request = new OrderProductRequest("productId1", 3);
		productCommandService.orderProduct(List.of(request));

		long afterStock = productQueryService.getProductById("productId1").getStock();
		assertThat(afterStock).isEqualTo(7);

	}

	@Test
	void orderProduct_Ex() {

		long beforeStock = productQueryService.getProductById("productId1").getStock();
		assertThat(beforeStock).isEqualTo(10);

		assertThrows(RuntimeException.class,
				() -> productCommandService.orderProduct(List.of(new OrderProductRequest("productId1", 11))));
		long afterStock = productQueryService.getProductById("productId1").getStock();
		assertThat(afterStock).isEqualTo(10);
	}

	@Test
	@DisplayName("재고감소 - 동시요청")
	void orderProductConcurrency() throws InterruptedException {

		String productId = "productId3";
		long beforeStock = productQueryService.getProductById(productId).getStock();
		assertThat(beforeStock).isEqualTo(30);

		int size = 10;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					productCommandService.orderProduct(List.of(new OrderProductRequest(productId, 1)));
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		es.shutdown();

		long afterStock = productQueryService.getProductById(productId).getStock();
		assertThat(afterStock).isEqualTo(20);
	}

	@Test
	void cancelOrderProduct() {
		long beforeStock = productQueryService.getProductById("productId2").getStock();
		assertThat(beforeStock).isEqualTo(20);

		CancelOrderProductRequest request = new CancelOrderProductRequest("productId2", 3);
		productCommandService.cancelOrderProduct(List.of(request));

		long afterStock = productQueryService.getProductById("productId2").getStock();
		assertThat(afterStock).isEqualTo(23);

	}

	@Test
	void likes() {
		LikesProductRequest user1 = new LikesProductRequest("productId1", "memberId1");
		LikesProductRequest user2 = new LikesProductRequest("productId1", "memberId2");

		productCommandService.likes(user1);
		assertThat(productQueryService.getProductById(user1.productId()).getLikes())
				.isEqualTo(1);

		productCommandService.likes(user2);
		assertThat(productQueryService.getProductById(user1.productId()).getLikes())
				.isEqualTo(2);

		productCommandService.likes(user2);
		assertThat(productQueryService.getProductById(user1.productId()).getLikes())
				.isEqualTo(1);

		productCommandService.likes(user1);
		assertThat(productQueryService.getProductById(user1.productId()).getLikes())
				.isZero();
	}

	@Test
	void myLikesProducts() {
		LikesProductRequest request1 = new LikesProductRequest("productId1", "memberId1");
		LikesProductRequest request2 = new LikesProductRequest("productId1", "memberId2");
		LikesProductRequest request3 = new LikesProductRequest("productId2", "memberId1");

		productCommandService.likes(request1);
		productCommandService.likes(request2);
		productCommandService.likes(request3);

		List<ProductDetailDto> memberId1 = productQueryService.findMyLikesProducts("memberId1");
		assertThat(memberId1).size().isEqualTo(2);
		List<ProductDetailDto> memberId2 = productQueryService.findMyLikesProducts("memberId2");
		assertThat(memberId2).size().isEqualTo(1);
	}

	@Test
	void likesConcurrency() throws InterruptedException {

		String productId = "productId1";
		assertThat(
				productQueryService.getProductById(productId).getLikes()
		).isZero();
		int size = 10;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				productCommandService.likes(new LikesProductRequest(productId, UUID.randomUUID().toString()));
				countDownLatch.countDown();
			});
		}

		countDownLatch.await();
		long end = System.currentTimeMillis();
		System.out.println("실행 시간: " + (end - start));

		es.shutdown();
		assertThat(
				productQueryService.getProductById(productId).getLikes()
		).isEqualTo(size);

	}

}