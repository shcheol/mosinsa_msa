package com.mosinsa.product.command.application;

import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.domain.*;
import com.mosinsa.product.infra.redis.StockOperand;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequests;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private static final String PREFIX = "product:";
	private final ProductRepository productRepository;
	private final StockPort stockPort;

	@Override
	@Transactional
	public void orderProduct(String customerId, OrderProductRequests orderProductRequests) {
		log.info("order: {}", orderProductRequests);
		List<OptionCombinations> optionCombinations = getProducts(orderProductRequests.orderProducts());
		validateStockStatus(optionCombinations);

		List<StockOperand> stockOperands = getStockOperands(orderProductRequests.orderProducts(), optionCombinations);
		StockResult stockResult = stockPort.tryDecrease(customerId, orderProductRequests.orderId(), stockOperands);
		if (StockResult.FAIL.equals(stockResult)) {
			throw new InvalidStockException();
		}
		checkSoldOut(optionCombinations);
	}

	private void checkSoldOut(List<OptionCombinations> products) {
		products.stream().filter(product -> stockPort.currentStock(PREFIX + product.getId()) == 0)
				.forEach(p -> p.getStock().updateSoldOut());
	}

	private List<StockOperand> getStockOperands(List<OrderProductRequests.OrderProductDto> orderProducts, List<OptionCombinations> optionCombinations) {
		return orderProducts.stream().map(op -> new StockOperand(PREFIX + optionCombinations
				.stream()
				.filter(oc -> oc.getProduct().getId().getId().equals(op.id()))
				.findAny()
				.orElseThrow()
				.getId(), op.quantity())).toList();
	}

	private List<OptionCombinations> getProducts(List<OrderProductRequests.OrderProductDto> orderProducts) {
		return orderProducts.stream().map(orderProduct ->
						productRepository.findProductDetailById(ProductId.of(orderProduct.id()))
								.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
								.getOptionCombinations()
								.stream()
								.filter(optionCombination ->
										new HashSet<>(optionCombination.getOptionCombinationValues()
												.stream()
												.mapToLong(ocv -> ocv.getProductOption().getId())
												.boxed()
												.toList())
												.containsAll(orderProduct.options()
														.stream()
														.map(OrderProductRequests.OrderProductDto.ProductOptionsDto::id)
														.toList()))
								.findAny()
								.orElseThrow())
				.toList();
	}

	private void validateStockStatus(List<OptionCombinations> products) {
		if (!products.stream().allMatch(p -> p.getStock().getStatus().equals(StockStatus.ON))) {
			throw new AlreadySoldOutException();
		}
	}

	@Override
	@Transactional
	public void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests) {
		log.info("cancelOrder: {}", requests);
		List<Product> products = getProductList(requests);


		List<StockOperand> stockOperands = requests.stream().map(r -> new StockOperand(r.productId(), r.quantity())).toList();
		StockResult stockResult = stockPort.tryIncrease(customerId, orderId, stockOperands);

		if (StockResult.FAIL.equals(stockResult)) {
			throw new InvalidStockException();
		}

//		products.forEach(product -> product.getStock().updateAvailable());
	}

	private List<Product> getProductList(List<CancelOrderProductRequest> requests) {
		return requests.stream().filter(request ->
						stockPort.currentStock(request.productId()) == 0 && request.quantity() > 0)
				.map(req -> productRepository.findProductDetailById(ProductId.of(req.productId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT)))
				.toList();
	}
}
