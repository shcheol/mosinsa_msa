package com.mosinsa.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Idempotence {

	@Id
	private String key;

}
