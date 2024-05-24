package com.mosinsa.product.domain.product;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class ProductId implements Serializable {

	@Column(name = "product_id")
	private String id;

	public static ProductId newId() {
		return new ProductId(UUID.randomUUID().toString());
	}


	public static ProductId of(String id) {
		return new ProductId(id);
	}

	private ProductId(String id){
		if (!StringUtils.hasText(id)){
			throw new IllegalArgumentException("invalid Id value");
		}
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductId productId = (ProductId) o;

		return Objects.equals(id, productId.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
