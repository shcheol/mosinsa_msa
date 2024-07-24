package com.mosinsa.product.command.application;

import com.mosinsa.product.infra.redis.StockOperand;
import com.mosinsa.product.infra.repository.StockHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	StockHistoryRepository repository;
	@Test
	void setAndGet() {

		String productId = "productId";
		long totalQuantity = 10L;
		stockService.setStock(productId, totalQuantity);
		long current = stockService.currentStock(productId);
		assertThat(totalQuantity).isEqualTo(current);
	}


	@Test
	void tryDecrease() {

		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 10L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		List<StockOperand> stockOperands = List.of(new StockOperand(productIdA, 1), new StockOperand(productIdB, 3));
		String orderId = UUID.randomUUID().toString();
		StockResult stockResult = stockService.tryDecrease("customerId1", orderId, stockOperands);

		assertThat(stockResult).isEqualTo(StockResult.SUCCESS);
		assertThat(stockService.currentStock(productIdA)).isEqualTo(totalQuantity - 1);
		assertThat(stockService.currentStock(productIdB)).isEqualTo(totalQuantity - 3);

		assertThat(repository.findStockHistoriesByOrderNum(orderId)).hasSize(2);
	}

	@Test
	@DisplayName("존재하지 않는 상품의 재고 감소 시도시 수량 감소 실패")
	void tryDecreaseFail() {

		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 10L;
		stockService.setStock(productIdA, totalQuantity);

		List<StockOperand> stockOperands = List.of(new StockOperand(productIdA, 1), new StockOperand(productIdB, 3));
		String orderId = UUID.randomUUID().toString();
		StockResult stockResult = stockService.tryDecrease("customerId1", orderId, stockOperands);

		assertThat(stockResult).isEqualTo(StockResult.FAIL);
		assertThat(stockService.currentStock(productIdA)).isEqualTo(totalQuantity);
		assertThat(stockService.currentStock(productIdB)).isZero();
		assertThat(repository.findStockHistoriesByOrderNum(orderId)).isEmpty();

		StockResult stockResult1 = stockService.tryDecrease("customerId1", orderId, List.of());
		assertThat(stockResult1).isEqualTo(StockResult.FAIL);
		assertThat(stockService.currentStock(productIdA)).isEqualTo(totalQuantity);
		assertThat(stockService.currentStock(productIdB)).isZero();
		assertThat(repository.findStockHistoriesByOrderNum(orderId)).isEmpty();
	}

	@Test
	@DisplayName("재고감소 - 재고 30일때 동시에 50건의 주문이 들어와도 남은 재고는 0이어야한다")
	void orderProductConcurrencyOverStock() throws InterruptedException {


		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 30L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		List<StockOperand> stockOperands = List.of(new StockOperand(productIdA, 1), new StockOperand(productIdB, 1));
		int size = 50;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					stockService.tryDecrease("customerId1", "orderId", stockOperands);
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		es.shutdown();

		long afterStock = stockService.currentStock(productIdA);
		assertThat(afterStock).isZero();
		long afterStock2 = stockService.currentStock(productIdB);
		assertThat(afterStock2).isZero();
	}

	@Test
	@DisplayName("재고감소 - 재고 범위내 동시 요청")
	void orderProductConcurrency() throws InterruptedException {


		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 30L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		List<StockOperand> stockOperands = List.of(new StockOperand(productIdA, 1), new StockOperand(productIdB, 1));
		String orderId = UUID.randomUUID().toString();

		int size = 10;
		ExecutorService es = Executors.newFixedThreadPool(size);
		CountDownLatch countDownLatch = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			es.execute(() -> {
				try {
					stockService.tryDecrease("customerId1", orderId, stockOperands);
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
		assertThat(repository.findStockHistoriesByOrderNum(orderId)).hasSize(20);
	}

	@Test
	@DisplayName(value = "주문 상품 중 한건이라도 재고가 부족하면 전체 주문 실패해서 재고는 변경되지 않는다.")
	void decreaseFail() {

		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 10L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);

		String orderId = UUID.randomUUID().toString();

		List<StockOperand> stockOperands = List.of(new StockOperand(productIdA, 3), new StockOperand(productIdB, 11));
		assertThat(stockService.tryDecrease("", orderId, stockOperands)).isEqualTo(StockResult.FAIL);

		assertThat(stockService.currentStock(productIdA)).isEqualTo(totalQuantity);
		assertThat(stockService.currentStock(productIdB)).isEqualTo(totalQuantity);

		List<StockOperand> stockOperands2 = List.of(new StockOperand(productIdA, 11), new StockOperand(productIdB, 3));
		assertThat(stockService.tryDecrease("", orderId, stockOperands2)).isEqualTo(StockResult.FAIL);

		assertThat(stockService.currentStock(productIdA)).isEqualTo(totalQuantity);
		assertThat(stockService.currentStock(productIdB)).isEqualTo(totalQuantity);
		assertThat(repository.findStockHistoriesByOrderNum(orderId)).isEmpty();
	}

	@Test
	void tryIncrease() {

		String productIdA = UUID.randomUUID().toString();
		String productIdB = UUID.randomUUID().toString();
		long totalQuantity = 10L;
		stockService.setStock(productIdA, totalQuantity);
		stockService.setStock(productIdB, totalQuantity);
		String orderId1 = UUID.randomUUID().toString();

		List<StockOperand> stockOperands = List.of(new StockOperand(productIdA, 1), new StockOperand(productIdB, 3));
		StockResult stockResult = stockService.tryIncrease("customerId1", orderId1, stockOperands);

		assertThat(stockResult).isEqualTo(StockResult.SUCCESS);
		assertThat(stockService.currentStock(productIdA)).isEqualTo(totalQuantity + 1);
		assertThat(stockService.currentStock(productIdB)).isEqualTo(totalQuantity + 3);
		assertThat(repository.findStockHistoriesByOrderNum(orderId1)).hasSize(2);

		String productIdC = UUID.randomUUID().toString();
		String orderId2 = UUID.randomUUID().toString();
		List<StockOperand> stockOperands2 = List.of(new StockOperand(productIdC, 3));
		StockResult stockResult2 = stockService.tryIncrease("customerId1", orderId2, stockOperands2);

		assertThat(stockResult2).isEqualTo(StockResult.SUCCESS);
		assertThat(stockService.currentStock(productIdC)).isEqualTo(3);
		assertThat(repository.findStockHistoriesByOrderNum(orderId2)).hasSize(1);
	}

	@Test
	void tryIncreaseFail() {

		String orderId = UUID.randomUUID().toString();
		StockResult stockResult1 = stockService.tryIncrease("customerId1", orderId, List.of());
		assertThat(stockResult1).isEqualTo(StockResult.FAIL);
		assertThat(repository.findStockHistoriesByOrderNum(orderId)).isEmpty();
	}

}