package com.mosinsa.product.query;

import com.mosinsa.category.application.CategoryService;
import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.infra.jpa.CategorySearchCondition;
import com.mosinsa.product.query.dto.*;
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
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

	private final CategoryService categoryService;
	private final ProductRepository productRepository;
	private final OptionCombinationService optionCombinationService;
	private final SalesService salesService;

	@Override
	public ProductDetails getProductById(String productId) {

		Product product = productRepository.findProductDetailById(ProductId.of(productId))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));

		List<ProductOptionDto> productOptionDtos = product.getProductOptions()
				.stream()
				.map(productOptions ->
						new ProductOptionDto(productOptions.getId(),
								productOptions.getOptionName(),
								productOptions.getProductOptionsValues()
										.stream()
										.map(ProductOptionsValueDto::of).toList()))
				.toList();

		OptionCombinationMapDto combinationMap = optionCombinationService.getCombinationMap(product);

		SalesDto sales = salesService.calculate(product);

		return new ProductDetails(product, productOptionDtos, combinationMap, sales);
	}

	@Override
	public Page<ProductSummary> findProductsByCondition(SearchCondition condition, Pageable pageable) {

		Set<String> subIds = StringUtils.hasText(condition.categoryId()) ?
				categoryService.getSubIds(condition.categoryId()) : new HashSet<>();
		return productRepository.findByCondition(new CategorySearchCondition(subIds, condition.sales()), pageable)
				.map(product -> {
					SalesDto salesDto = salesService.calculate(product);
					return ProductSummary.of(product, salesDto);
				});
	}

	@Override
	public Page<ProductSummary> findMyProducts(String memberId, Pageable pageable) {

		return productRepository.findMyProducts(memberId, pageable)
				.map(product -> {
					SalesDto salesDto = salesService.calculate(product);
					return ProductSummary.of(product, salesDto);
				});
	}
}
