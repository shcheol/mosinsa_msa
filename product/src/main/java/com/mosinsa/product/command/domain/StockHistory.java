package com.mosinsa.product.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(of = "id")
public class StockHistory {

	@Id
	@Column(name = "stock_history")
	private String id;



}
