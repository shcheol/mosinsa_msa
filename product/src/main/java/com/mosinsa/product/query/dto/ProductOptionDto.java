package com.mosinsa.product.query.dto;

import com.mosinsa.product.command.domain.ProductOption;

import java.util.List;

public record ProductOptionDto(Long id, ProductOption optionName, List<ProductOptionsValueDto> productOptionsValues) {

}
