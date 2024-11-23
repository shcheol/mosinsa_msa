package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OptionCombinationValues extends BaseIdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_combinations_id")
	private OptionCombinations optionCombinations;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_options_value_id")
	private ProductOptionsValue productOption;

	public static OptionCombinationValues of(ProductOptionsValue productOptionsValue){
		OptionCombinationValues optionCombinationValues = new OptionCombinationValues();
		optionCombinationValues.productOption = productOptionsValue;
		return optionCombinationValues;
	}

	protected void setOptionCombinations(OptionCombinations optionCombinations){
		this.optionCombinations = optionCombinations;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
