package com.mosinsa.product.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Brand extends BaseIdEntity {

	private String name;

	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	private List<Product> products = new ArrayList<>();

	protected Brand() {
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
