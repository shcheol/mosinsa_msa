package com.mosinsa.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.db.dto.OrderDto;
import com.mosinsa.product.db.dto.OrderProductDto;
import com.mosinsa.product.db.dto.ProductDto;
import com.mosinsa.product.db.entity.Product;
import com.mosinsa.product.service.messegequeue.KafkaProducer;
import com.mosinsa.product.db.repository.IdempotentComponent;
import com.mosinsa.product.db.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductService {

    private final KafkaProducer kafkaProducer;
    private final ProductRepository productRepository;
	private final IdempotentComponent idempotentComponent;

    @Transactional
    public ProductDto addProduct(ProductAddRequest productAddRequest) {

        Product save = productRepository.save(new Product(productAddRequest));
        return new ProductDto(save);
    }

    @Transactional
    public void updateProduct(String productId, ProductUpdateRequest productUpdateRequest) {
        Product findProduct = productRepository.findById(productId).orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));
        findProduct.change(productUpdateRequest);
    }

    public ProductDto getProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));
        return new ProductDto(product);
    }


    public Page<ProductDto> findAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable).map(ProductDto::new);
    }

    @Transactional
    public void orderProduct(String idempotencyKey, OrderDto orderDto){
		if (!idempotencyKey.isBlank()){
			String s = idempotentComponent.get(idempotencyKey);
			if (s != null && !s.isBlank()){
				ObjectMapper objectMapper = new ObjectMapper();
				OrderDto orderDto1 = null;
				try {
					orderDto1 = objectMapper.readValue(s, OrderDto.class);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
				kafkaProducer.completeTransaction("mosinsa-product-order-commit", idempotencyKey, orderDto1);
				return;
			}
		}

        List<OrderProductDto> tempOrderProductDtos = new ArrayList<>();
        try {
            assert orderDto != null;
            List<OrderProductDto> orderProductDtos = orderDto.getOrderProducts();
            for (OrderProductDto orderProductDto : orderProductDtos) {
                log.info("orderProductDto {}",orderProductDto);
                int orderCount = orderProductDto.getOrderCount();

                log.info("orderCount = {}", orderCount);
                Product product = productRepository.findById(orderProductDto.getProductDto().getProductId()).orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));
                product.removeStock(orderCount);
                tempOrderProductDtos.add(orderProductDto);
            }
            kafkaProducer.completeTransaction("mosinsa-product-order-commit", idempotencyKey, orderDto);
        }catch (Exception e){
			log.error("order product fail",e);
            for (OrderProductDto tempOrderProductDto : tempOrderProductDtos) {
                int orderCount = tempOrderProductDto.getOrderCount();
                Product product = productRepository.findById(tempOrderProductDto.getProductDto().getProductId()).orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));
                product.addStock(orderCount);
            }

            OrderDto rollbackOrder =
                    new OrderDto(orderDto.getId(), orderDto.getCustomerId(), orderDto.getTotalPrice(), orderDto.getStatus(), tempOrderProductDtos);
            kafkaProducer.completeTransaction("mosinsa-product-order-rollback", idempotencyKey, rollbackOrder);
        }
    }

    @Transactional
    public void cancelOrder(OrderDto orderDto) {
        assert orderDto != null;
        List<OrderProductDto> orderProducts = orderDto.getOrderProducts();
        for (OrderProductDto orderProductDto : orderProducts) {
            int orderCount = orderProductDto.getOrderCount();

            log.info("orderCount = {}", orderCount);
            Product product = productRepository.findById(orderProductDto.getProductDto().getProductId()).orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));

            product.addStock(orderCount);
        }
    }
}
