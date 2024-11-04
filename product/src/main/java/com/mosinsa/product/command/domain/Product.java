package com.mosinsa.product.command.domain;

import com.mosinsa.category.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AuditingEntity {

	@EmbeddedId
	private ProductId id;
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id")
	private Brand brand;
	@Convert(converter = MoneyConverter.class)
	@Column(name = "price")
	private Money price;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private List<ProductOptions> productOptions = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private List<OptionCombinations> optionCombinations = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private List<Sales> sales = new ArrayList<>();

	public static Product of(String name, int price, Category category) {
		Product product = new Product();
		product.id = ProductId.newId();
		product.name = name;
		product.price = Money.of(price);
		product.category = category;
		return product;
	}

	public void addOptions(List<ProductOptions> options){
		for (ProductOptions option : options) {
			this.productOptions.add(option);
			option.setProduct(this);
		}
	}

	public void addCombinations(List<OptionCombinations> optionCombinations){
		for (OptionCombinations optionCombination : optionCombinations) {
			this.optionCombinations.add(optionCombination);
			optionCombination.setProduct(this);
		}
	}

	public void addSales(List<Sales> sales){
		this.sales.clear();
		for (Sales sale : sales) {
			this.sales.add(sale);
			sale.setProduct(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Product product = (Product) o;
		return id != null && Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
