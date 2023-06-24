package com.mosinsa.product.service;

import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.dto.OrderDto;
import com.mosinsa.product.dto.OrderProductDto;
import com.mosinsa.product.dto.ProductDto;
import com.mosinsa.product.entity.Product;
import com.mosinsa.product.messegequeue.KafkaProducer;
import com.mosinsa.product.repository.ProductRepository;
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

    @Transactional
    public ProductDto addProduct(ProductAddRequest productAddRequest) {

        Product save = productRepository.save(new Product(productAddRequest));
        return new ProductDto(save);
    }

    @Transactional
    public void updateProduct(String productId, ProductUpdateRequest productUpdateRequest) {
        Product findProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("수정할 상품이 없습니다."));
        findProduct.change(productUpdateRequest);
    }

    public ProductDto getProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("조회한 상품이 없습니다."));
        return new ProductDto(product);
    }

    public Page<ProductDto> getProducts(Pageable pageable) {

        return productRepository.findAll(pageable).map(ProductDto::new);
    }

    @Transactional
    public void orderProduct(OrderDto orderDto) {

        List<OrderProductDto> tempOrderProductDtos = new ArrayList<>();
        try {
            assert orderDto != null;
            List<OrderProductDto> orderProductDtos = orderDto.getOrderProducts();
            for (OrderProductDto orderProductDto : orderProductDtos) {
                log.info("orderProductDto {}",orderProductDto);
                int orderCount = orderProductDto.getOrderCount();

                log.info("orderCount = {}", orderCount);
                Product product = productRepository.findById(orderProductDto.getProductDto().getProductId()).orElseThrow(() -> new IllegalArgumentException("조회한 상품이 없습니다."));
                product.removeStock(orderCount);
                tempOrderProductDtos.add(orderProductDto);
            }
        }catch (Exception e){
            for (OrderProductDto tempOrderProductDto : tempOrderProductDtos) {
                int orderCount = tempOrderProductDto.getOrderCount();
                Product product = productRepository.findById(tempOrderProductDto.getProductDto().getProductId()).orElseThrow(() -> new IllegalArgumentException("조회한 상품이 없습니다."));
                product.addStock(orderCount);
            }

            OrderDto rollbackOrder =
                    new OrderDto(orderDto.getId(), orderDto.getCustomerId(), orderDto.getTotalPrice(), orderDto.getStatus(), tempOrderProductDtos);
            kafkaProducer.rollbackOrder("mosinsa-product-order-rollback", rollbackOrder);
        }
    }

    @Transactional
    public void cancelOrder(OrderDto orderDto) {
        assert orderDto != null;
        List<OrderProductDto> orderProducts = orderDto.getOrderProducts();
        for (OrderProductDto orderProductDto : orderProducts) {
            int orderCount = orderProductDto.getOrderCount();

            log.info("orderCount = {}", orderCount);
            Product product = productRepository.findById(orderProductDto.getProductDto().getProductId()).orElseThrow(() -> new IllegalArgumentException("조회한 상품이 없습니다."));

            product.addStock(orderCount);
        }
    }
}
