package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.ProductOption;
import com.mosinsa.product.command.domain.ProductOptions;
import com.mosinsa.product.query.dto.ProductOptionDto;

public interface ProductOptionStrategy {

	ProductOptionDto getOptionValues(ProductOptions productOptions);

	boolean isSupport(ProductOption productOption);
}
