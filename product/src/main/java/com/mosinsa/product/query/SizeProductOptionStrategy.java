package com.mosinsa.product.query;

import com.mosinsa.product.command.application.StockService;
import com.mosinsa.product.command.domain.ProductOption;
import com.mosinsa.product.command.domain.ProductOptions;
import com.mosinsa.product.command.domain.ProductOptionsValue;
import com.mosinsa.product.query.dto.ProductOptionDto;
import com.mosinsa.product.query.dto.ProductOptionsValueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SizeProductOptionStrategy implements ProductOptionStrategy{

	private final StockService stockService;

	@Override
	public ProductOptionDto getOptionValues(ProductOptions productOptions) {
		List<ProductOptionsValue> productOptionsValues = productOptions.getProductOptionsValues();
		List<ProductOptionsValueDto> productOptionsValueDtos = productOptionsValues.stream().map(ProductOptionsValueDto::of).toList();

		return new ProductOptionDto(productOptions.getOptionName(), productOptionsValueDtos);
	}

	@Override
	public boolean isSupport(ProductOption productOption) {
		return ProductOption.SIZE.equals(productOption);
	}
}
