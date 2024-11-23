package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.query.dto.OptionCombinationMapDto;
import com.mosinsa.product.query.dto.StockDto;
import org.springframework.stereotype.Component;

@Component
public class OptionCombinationService {


	public OptionCombinationMapDto getCombinationMap(Product product) {
		return new OptionCombinationMapDto(product.getOptionCombinations()
				.stream()
				.map(oc -> new OptionCombinationMapDto.OptionCombinationDto(oc.getId(),
						oc.getOptionCombinationValues().stream().map(ocv-> ocv.getProductOption().getId()).toList(),
						new StockDto(oc.getStock().getTotal(), oc.getStock().getTotal(), oc.getStock().getStatus())))
				.toList());

	}
}
