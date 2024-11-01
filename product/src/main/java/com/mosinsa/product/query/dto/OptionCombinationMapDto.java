package com.mosinsa.product.query.dto;

import java.util.List;

public record OptionCombinationMapDto(List<OptionCombinationDto> optionCombinations) {

	public record OptionCombinationDto(Long combinationId, List<Long> combinationValuesIds, StockDto stock){

	}
}
