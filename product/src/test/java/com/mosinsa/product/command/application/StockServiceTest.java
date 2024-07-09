package com.mosinsa.product.command.application;

import com.mosinsa.product.infra.redis.StockOperand;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockServiceTest {

	@Autowired
	StockService stockService;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Test
	void setAndGet() {

		String productId = "productId";
		long totalQuantity = 10L;
		stockService.setStock(productId, totalQuantity);

		long current = stockService.currentStock(productId);

		assertThat(totalQuantity).isEqualTo(current);
	}


	@Test
	void decrease() {

		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 10L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		stockService.decreaseStocks("customerId1", "orderId1", List.of(
				new StockOperand(productIdA, 1),
				new StockOperand(productIdB, 3)
		));

		long current = stockService.currentStock(productIdA);
		assertThat(totalQuantity - 1).isEqualTo(current);
		long currentB = stockService.currentStock(productIdB);
		assertThat(totalQuantity - 3).isEqualTo(currentB);
	}

	@Test
	@DisplayName("재고감소 - 재고 30일때 50건의 주문이 들어와도 남은 재고는 0이어야한다")
	void orderProductConcurrencyOverStock() throws InterruptedException {


		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 30L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		int size = 50;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					stockService.decreaseStocks("customerId1", "orderId1", List.of(
							new StockOperand(productIdA, 1),
							new StockOperand(productIdB, 1)
					));
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		es.shutdown();

		long afterStock = stockService.currentStock(productIdA);
		assertThat(afterStock).isEqualTo(0);
		long afterStock2 = stockService.currentStock(productIdB);
		assertThat(afterStock2).isEqualTo(0);
	}

	@Test
	@DisplayName("재고감소 - 재고 범위내 동시 요청")
	void orderProductConcurrency() throws InterruptedException {


		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 30L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		int size = 10;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					stockService.decreaseStocks("customerId1", "orderId1", List.of(
							new StockOperand(productIdA, 1),
							new StockOperand(productIdB, 1)
					));
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		es.shutdown();

		long afterStock = stockService.currentStock(productIdA);
		assertThat(afterStock).isEqualTo(20);
		long afterStock2 = stockService.currentStock(productIdB);
		assertThat(afterStock2).isEqualTo(20);
	}

	@Test
	@DisplayName(value = "주문 상품 중 한건이라도 재고가 부족하면 전체 주문 실패")
	void decreaseFail() {

		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 10L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		Assertions.assertThrows(RuntimeException.class, () ->
				stockService.decreaseStocks("", "", List.of(
						new StockOperand(productIdA, 3),
						new StockOperand(productIdB, 11)
				)));

//		stockService.decreaseStocks("", "", List.of(
//				new StockOperand(productIdA, 3),
//				new StockOperand(productIdB, 11)
//		));

		long current = stockService.currentStock(productIdA);
		assertThat(totalQuantity).isEqualTo(current);
		long currentB = stockService.currentStock(productIdB);
		assertThat(totalQuantity).isEqualTo(currentB);
	}

}