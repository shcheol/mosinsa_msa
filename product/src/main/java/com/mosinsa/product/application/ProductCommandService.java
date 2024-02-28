package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDetailDto;
import com.mosinsa.product.common.aop.RedissonLock;
import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.domain.product.Product;
import com.mosinsa.product.domain.product.ProductId;
import com.mosinsa.product.infra.repository.ProductRepository;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.LikesProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService {

	private final ProductLikesService productLikesService;
	private final ProductRepository productRepository;
	private final CategoryService categoryService;
	private static final String STOCK_LOCK_KEY = "stockLock";
	private static final String LIKES_LOCK_KEY = "likesLock";

	public ProductDetailDto createProduct(CreateProductRequest request) {
		Category category = categoryService.getCategory(request.category());
		return new ProductDetailDto(
				productRepository.save(
						Product.create(request.name(),
								request.price(),
								category,
								request.stock())));
	}

	@RedissonLock(value = STOCK_LOCK_KEY)
	public void orderProduct(List<OrderProductRequest> requests) {

		log.info("order: {}", requests);
		requests.forEach(request ->
				productRepository.findProductDetailById(ProductId.of(request.productId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.decreaseStock(request.quantity()));

	}

	@RedissonLock(value = STOCK_LOCK_KEY)
	public void cancelOrderProduct(List<CancelOrderProductRequest> requests) {
		log.info("cancelOrder: {}", requests);
		requests.forEach(request ->
				productRepository.findProductDetailById(ProductId.of(request.productId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.increaseStock(request.quantity()));
	}

	@RedissonLock(value = LIKES_LOCK_KEY)
	public void likes(LikesProductRequest request) {

		try {
			productLikesService.likes(request);
		} catch (DataIntegrityViolationException e){
			productLikesService.likesCancel(request);
		}
	}
}
