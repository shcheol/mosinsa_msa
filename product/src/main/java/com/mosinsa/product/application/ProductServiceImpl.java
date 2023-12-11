package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.OrderDto;
import com.mosinsa.product.application.dto.OrderProductDto;
import com.mosinsa.product.application.dto.ProductDto;
import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.common.wrapper.PageWrapper;
import com.mosinsa.product.domain.category.Category;
import com.mosinsa.product.domain.product.Product;
import com.mosinsa.product.domain.product.ProductId;
import com.mosinsa.product.infra.kafka.KafkaProducer;
import com.mosinsa.product.infra.repository.ProductRepository;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.DecreaseStockRequest;
import com.mosinsa.product.ui.request.IncreaseStockRequest;
import com.mosinsa.product.ui.request.LikesProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final KafkaProducer kafkaProducer;
	private final ProductRepository productRepository;
	private final CategoryService categoryService;

	@Value("${mosinsa.topic.order.rollback}")
	private String rollbackTopic;

	@Value("${mosinsa.topic.order.commit}")
	private String commitTopic;

	@Override
	public ProductDto createProduct(CreateProductRequest request) {

		Category category = categoryService.getCategory(request.categoryId());

		return new ProductDto(
				productRepository.save(
						Product.create(request.name(),
								request.price(),
								category,
								request.stock())
				));
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
	public void likes(LikesProductRequest request) {
		productRepository.findById(ProductId.of(request.productId()))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
				.likes(request.memberId());
	}

	@Override
	public void increaseStock(IncreaseStockRequest request) {
		productRepository.findById(ProductId.of(request.productId()))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
				.increaseStock(request.stock());
	}

	@Override
	public void decreaseStock(DecreaseStockRequest request) {
		productRepository.findById(ProductId.of(request.productId()))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
				.decreaseStock(request.stock());
	}

	public void orderProduct(OrderDto orderDto) {

		List<OrderProductDto> tempOrderProductDtos = new ArrayList<>();
		try {
			orderDto.getOrderProducts().forEach(op -> {
				log.info("orderProductDto {}", op);
				productRepository.findById(ProductId.of(op.getProductDto().getProductId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.decreaseStock(op.getOrderCount());
				tempOrderProductDtos.add(op);
			});
			kafkaProducer.completeTransaction(commitTopic, orderDto);
		} catch (Exception e) {
			log.error("order product fail", e);
			tempOrderProductDtos.forEach(op -> productRepository.findById(ProductId.of(op.getProductDto().getProductId()))
					.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
					.increaseStock(op.getOrderCount()));

			OrderDto rollbackOrder =
					new OrderDto(orderDto.getOrderId(), orderDto.getCustomerId(), orderDto.getTotalPrice(), orderDto.getStatus(), tempOrderProductDtos);
			kafkaProducer.completeTransaction(rollbackTopic, rollbackOrder);
		}
	}

	public void cancelOrder(OrderDto orderDto) {
		log.info("cancelOrder: {}", orderDto);
		orderDto.getOrderProducts().forEach(op ->
				productRepository.findById(ProductId.of(op.getProductDto().getProductId()))
						.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
						.increaseStock(op.getOrderCount()));
	}
}
