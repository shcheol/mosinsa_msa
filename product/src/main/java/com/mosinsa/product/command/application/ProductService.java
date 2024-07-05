package com.mosinsa.product.command.application;

import com.mosinsa.category.Category;
import com.mosinsa.category.CategoryService;
import com.mosinsa.common.aop.RedissonLock;
import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.infra.repository.ProductRepository;
import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryService categoryService;
	private static final String STOCK_LOCK_KEY = "stockLock";

	@Transactional
	public ProductDetailDto createProduct(CreateProductRequest request) {
		Category category = categoryService.getCategory(request.category());
		return new ProductDetailDto(
				productRepository.save(
						Product.create(request.name(),
								request.price(),
								category,
								request.stock())));
	}

	@Transactional
	@RedissonLock(value = STOCK_LOCK_KEY)
	public void orderProduct(String customerId, String orderId, List<OrderProductRequest> requests) {


		//TODO
		log.info("order: {}", requests);
		requests.forEach(request ->
				productRepository.findProductDetailById(ProductId.of(request.productId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.decreaseStock(request.quantity()));

	}

	@RedissonLock(value = STOCK_LOCK_KEY)
	@Transactional
	public void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests) {
		log.info("cancelOrder: {}", requests);
		requests.forEach(request ->
				productRepository.findProductDetailById(ProductId.of(request.productId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.increaseStock(request.quantity()));
	}
}
