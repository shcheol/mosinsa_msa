package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class OptionCombinations extends BaseIdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "stock_id")
	private Stock stock;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "optionCombinations")
	private List<OptionCombinationValues> optionCombinationValues = new ArrayList<>();

	public static OptionCombinations of (Stock stock){
		OptionCombinations optionCombinations = new OptionCombinations();
		optionCombinations.stock = stock;
		return optionCombinations;
	}

	public void addOptionCombinationValues(List<OptionCombinationValues> optionCombinationValues){
		for (OptionCombinationValues optionCombinationValue : optionCombinationValues) {
			this.optionCombinationValues.add(optionCombinationValue);
			optionCombinationValue.setOptionCombinations(this);
		}
	}

	protected void setProduct(Product product) {
		this.product = product;
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
