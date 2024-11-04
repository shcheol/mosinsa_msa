package com.mosinsa.product.ui.request;

import com.mosinsa.product.command.domain.ChangeType;
import com.mosinsa.product.command.domain.ProductOption;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateProductRequest(@NotBlank String name,
								   String description,

								   @NotBlank int price,
								   @NotBlank String categoryId,
								   List<ProductOptionsDto> productOptions){


	public record ProductOptionsDto(ProductOption productOption, List<ProductOptionValueDto> productOptionValues) {
		public record ProductOptionValueDto(String optionsValue, int changePrice, ChangeType changeType){

		}
	}

}
