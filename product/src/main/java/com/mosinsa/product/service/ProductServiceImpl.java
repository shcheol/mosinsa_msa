package com.mosinsa.product.service;

import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.db.dto.OrderDto;
import com.mosinsa.product.db.dto.OrderProductDto;
import com.mosinsa.product.db.dto.ProductDto;
import com.mosinsa.product.db.entity.Product;
import com.mosinsa.product.db.repository.ProductRepository;
import com.mosinsa.product.service.kafka.KafkaProducer;
import com.mosinsa.product.service.wrapper.PageWrapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final KafkaProducer kafkaProducer;
    private final ProductRepository productRepository;

    @Value("${mosinsa.topic.order.rollback}")
    private String rollbackTopic;

    @Value("${mosinsa.topic.order.commit}")
    private String commitTopic;

    @Override
    @Transactional
    public ProductDto addProduct(ProductAddRequest productAddRequest) {

        return new ProductDto(productRepository.save(new Product(productAddRequest)));
    }

    @Override
    @CacheEvict(value = "product", key = "#productId")
    @Transactional
    public void updateProduct(String productId, ProductUpdateRequest productUpdateRequest) {
        Product findProduct = productRepository.findById(productId).orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));
        findProduct.change(productUpdateRequest);
    }

    @Override
    @Cacheable(value = "product")
    public ProductDto findProductById(String productId) {
        log.info("findProductById");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));
        return new ProductDto(product);
    }

    @Override
    @Cacheable(value = "products", key = "#pageable.pageSize +'-' + #pageable.pageNumber")
    public Page<ProductDto> findAllProducts(Pageable pageable) {

        return new PageWrapper<>(productRepository.findAll(pageable).map(ProductDto::new));
    }

    @Transactional
    public void orderProduct(OrderDto orderDto) {

        List<OrderProductDto> tempOrderProductDtos = new ArrayList<>();
        try{
            orderDto.getOrderProducts().forEach(op -> {
                log.info("orderProductDto {}", op);
                productRepository.findById(op.getProductDto().getProductId())
                        .orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
                        .removeStock(op.getOrderCount());
                tempOrderProductDtos.add(op);
            });
            kafkaProducer.completeTransaction(commitTopic, orderDto);
        }catch (Exception e){
            log.error("order product fail", e);
            tempOrderProductDtos.forEach(op -> {
                productRepository.findById(op.getProductDto().getProductId())
                        .orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
                        .addStock(op.getOrderCount());
            });

            OrderDto rollbackOrder =
                    new OrderDto(orderDto.getOrderId(), orderDto.getCustomerId(), orderDto.getTotalPrice(), orderDto.getStatus(), tempOrderProductDtos);
            kafkaProducer.completeTransaction(rollbackTopic, rollbackOrder);
        }
    }

    @Transactional
    public void cancelOrder(OrderDto orderDto) {
        log.info("cancelOrder: {}", orderDto);
        orderDto.getOrderProducts().forEach(op ->
                productRepository.findById(op.getProductDto().getProductId())
                        .orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))
                        .addStock(op.getOrderCount()));
    }
}
