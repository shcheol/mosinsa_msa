package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.SalesPolicyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesPolicyStrategyProvider {

	private final List<SalesPolicyStrategy> salesPolicyStrategyList;

	public SalesPolicyStrategy provide(SalesPolicyType salesPolicyType) {
		return salesPolicyStrategyList.stream()
				.filter(salesPolicyStrategy -> salesPolicyStrategy.isSupport(salesPolicyType))
				.findAny().orElseThrow();
	}
}
