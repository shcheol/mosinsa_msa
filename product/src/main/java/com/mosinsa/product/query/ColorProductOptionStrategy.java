package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.ProductOption;
import com.mosinsa.product.command.domain.ProductOptions;
import com.mosinsa.product.command.domain.ProductOptionsValue;
import com.mosinsa.product.query.dto.ProductOptionDto;
import com.mosinsa.product.query.dto.ProductOptionsValueDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColorProductOptionStrategy implements ProductOptionStrategy{
	@Override
	public ProductOptionDto getOptionValues(ProductOptions productOptions) {
		List<ProductOptionsValue> productOptionsValues = productOptions.getProductOptionsValues();
		List<ProductOptionsValueDto> productOptionsValueDtos = productOptionsValues.stream().map(ProductOptionsValueDto::of).toList();

		return new ProductOptionDto(productOptions.getOptionName(), productOptionsValueDtos);
	}

	@Override
	public boolean isSupport(ProductOption productOption) {
		return ProductOption.COLOR.equals(productOption);
	}
}
