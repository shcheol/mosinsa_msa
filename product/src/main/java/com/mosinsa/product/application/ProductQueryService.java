package com.mosinsa.product.application;

import com.mosinsa.product.application.dto.ProductDetailDto;
import com.mosinsa.product.application.dto.ProductQueryDto;
import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.domain.product.ProductId;
import com.mosinsa.product.infra.repository.ProductRepository;
import com.mosinsa.product.ui.request.SearchCondition;
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
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductDetailDto getProductById(String productId) {
        return new ProductDetailDto(productRepository.findProductDetailById(ProductId.of(productId))
                .orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT)));
    }

    public List<ProductDetailDto> findMyLikesProducts(String customerId) {
        return productRepository.findMyLikesProduct(customerId);
    }

    public Page<ProductQueryDto> findProductsByCondition(SearchCondition condition, Pageable pageable) {
        return productRepository.findByCondition(condition, pageable);
    }

}
