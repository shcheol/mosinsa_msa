package com.mosinsa.product.query;

import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.application.StockService;
import com.mosinsa.product.command.application.dto.ProductQueryDto;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.ProductRepository;
import com.mosinsa.product.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

	private final ProductRepository productRepository;
	private final StockService stockService;

	@Override
	public ProductDetailDto getProductById(String productId) {
		long currentStock = stockService.currentStock(productId);
		return new ProductDetailDto(productRepository.findProductDetailById(ProductId.of(productId))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT)), currentStock);
	}

	@Override
	public Page<ProductQueryDto> findProductsByCondition(SearchCondition condition, Pageable pageable) {
		return productRepository.findByCondition(condition, pageable);
	}

	@Override
	public Page<ProductQueryDto> findMyProducts(String memberId, Pageable pageable) {

		Page<ProductQueryDto> myProducts = productRepository.findMyProducts(memberId, pageable);
		if (myProducts.getContent().isEmpty()) {
			throw new ProductException(ProductError.NOT_FOUNT_PRODUCT);
		}
		return myProducts;
	}
}
