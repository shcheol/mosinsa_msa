package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OptionStrategyFinder {

	private final List<ProductOptionStrategy> productOptionStrategyList;

	public ProductOptionStrategy findOptionStrategy(ProductOption productOption){
		return productOptionStrategyList
				.stream()
				.filter(productOptionStrategy -> productOptionStrategy.isSupport(productOption))
				.findAny()
				.orElseThrow();
	}
}
