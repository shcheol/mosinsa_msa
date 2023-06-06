package com.mosinsa.product.service;

import com.mosinsa.product.controller.request.ProductAddRequest;
import com.mosinsa.product.controller.request.ProductUpdateRequest;
import com.mosinsa.product.dto.ProductDto;
import com.mosinsa.product.entity.Product;
import com.mosinsa.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDto addProduct(ProductAddRequest productAddRequest) {

        Product save = productRepository.save(new Product(productAddRequest));
        return new ProductDto(save);
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Product findProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("수정할 상품이 없습니다."));
        findProduct.change(productUpdateRequest);
    }

    public ProductDto getProduct(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("조회한 상품이 없습니다."));
        return new ProductDto(product);
    }

    public Page<ProductDto> getProducts(Pageable pageable) {

        return productRepository.findAll(pageable).map(ProductDto::new);
    }
}
