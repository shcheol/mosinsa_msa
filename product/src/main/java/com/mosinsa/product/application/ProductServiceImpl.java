package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.common.aop.RedissonLock;
import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.common.wrapper.PageWrapper;
import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.domain.product.Product;
import com.mosinsa.product.domain.product.ProductId;
import com.mosinsa.product.infra.repository.ProductRepository;
import com.mosinsa.product.ui.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryService categoryService;
	private static final String STOCK_LOCK_KEY = "stockLock";
	private static final String LIKES_LOCK_KEY = "likesLock";

	@Override
	public ProductDto createProduct(CreateProductRequest request) {

		Category category = categoryService.getCategory(request.category());

		return new ProductDto(
				productRepository.save(
						Product.create(request.name(),
								request.price(),
								category,
								request.stock())));
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDto getProductById(String productId) {
		return new ProductDto(productRepository.findById(ProductId.of(productId))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT)));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductDto> getAllProducts(Pageable pageable) {
		return new PageWrapper<>(productRepository.findAll(pageable).map(ProductDto::new));
	}

	@Override
	public List<ProductDto> findMyLikesProducts(String customerId) {
		return productRepository.findLikesProduct(customerId);
	}

	@Override
	public Page<ProductDto> findProductsByCondition(SearchCondition condition, Pageable pageable) {
		return productRepository.findByCondition(condition, pageable);
	}


	@Override
	@RedissonLock(value = STOCK_LOCK_KEY)
	public void orderProduct(List<OrderProductRequest> requests) {

		log.info("order: {}", requests);
		requests.forEach(request ->
				productRepository.findById(ProductId.of(request.productId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.decreaseStock(request.quantity()));

	}

	@Override
	@RedissonLock(value = STOCK_LOCK_KEY)
	public void cancelOrderProduct(List<CancelOrderProductRequest> requests) {
		log.info("cancelOrder: {}", requests);
		requests.forEach(request ->
				productRepository.findById(ProductId.of(request.productId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.increaseStock(request.quantity()));
	}

	@Override
	@RedissonLock(value = LIKES_LOCK_KEY)
	public void likes(LikesProductRequest request) {
		productRepository.findById(ProductId.of(request.productId()))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
				.likes(request.memberId());
	}
}
