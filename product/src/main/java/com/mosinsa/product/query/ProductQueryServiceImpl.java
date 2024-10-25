package com.mosinsa.product.query;

import com.mosinsa.category.application.CategoryService;
import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.application.StockService;
import com.mosinsa.product.infra.jpa.CategorySearchCondition;
import com.mosinsa.product.query.dto.ProductDetails;
import com.mosinsa.product.query.dto.ProductSummary;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.ProductRepository;
import com.mosinsa.product.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

	private final CategoryService categoryService;
	private final ProductRepository productRepository;
	private final StockService stockService;

	@Override
	public ProductDetails getProductById(String productId) {
		long currentStock = stockService.currentStock(productId);
		return new ProductDetails(productRepository.findProductDetailById(ProductId.of(productId))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT)), currentStock);
	}

	@Override
	public Page<ProductSummary> findProductsByCondition(SearchCondition condition, Pageable pageable) {

		Set<String> subIds = StringUtils.hasText(condition.categoryId()) ? categoryService.getSubIds(condition.categoryId()) : new HashSet<>();
		return productRepository.findByCondition(new CategorySearchCondition(subIds), pageable);
	}

	@Override
	public Page<ProductSummary> findMyProducts(String memberId, Pageable pageable) {

		Page<ProductSummary> myProducts = productRepository.findMyProducts(memberId, pageable);
		if (myProducts.getContent().isEmpty()) {
			throw new ProductException(ProductError.NOT_FOUNT_PRODUCT);
		}
		return myProducts;
	}
}
